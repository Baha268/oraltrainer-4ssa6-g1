package com.esprit.oral.progress.service;

import com.esprit.oral.progress.client.UserClient;
import com.esprit.oral.progress.dto.ProgressDtos.DashboardResponse;
import com.esprit.oral.progress.dto.ProgressDtos.GapResponse;
import com.esprit.oral.progress.dto.ProgressDtos.HistoryPoint;
import com.esprit.oral.progress.dto.ProgressDtos.MemberRow;
import com.esprit.oral.progress.dto.ProgressDtos.ProgressResponse;
import com.esprit.oral.progress.dto.ProgressDtos.Scores;
import com.esprit.oral.progress.dto.ProgressDtos.SnapshotResponse;
import com.esprit.oral.progress.entity.AlertStatus;
import com.esprit.oral.progress.entity.ProgressSnapshot;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.BadRequestException;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.NotFoundException;
import com.esprit.oral.progress.repository.AlertRepository;
import com.esprit.oral.progress.repository.ProgressSnapshotRepository;
import com.esprit.oral.progress.util.Levels;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProgressService {

    public static final List<String> CRITERIA =
            List.of("pronunciation", "fluency", "intonation", "lexicon", "grammar", "coherence");

    private final ProgressSnapshotRepository snapshots;
    private final AlertRepository alerts;
    private final UserClient userClient;

    public ProgressService(ProgressSnapshotRepository snapshots, AlertRepository alerts, UserClient userClient) {
        this.snapshots = snapshots;
        this.alerts = alerts;
        this.userClient = userClient;
    }

    public ProgressResponse getProgress(Long userId) {
        List<ProgressSnapshot> all = snapshots.findByUserIdOrderByRecordedAtDesc(userId);
        if (all.isEmpty()) {
            throw new NotFoundException("No progress found for user " + userId);
        }
        ProgressSnapshot latest = all.get(0);
        return new ProgressResponse(userId, all.size(), toResponse(latest), average(latest));
    }

    public List<HistoryPoint> getHistory(Long userId, LocalDate from, LocalDate to, String criterion) {
        String wanted = criterion == null || criterion.isBlank() ? null : criterion.trim().toLowerCase();
        if (wanted != null && !CRITERIA.contains(wanted)) {
            throw new BadRequestException("Unknown criterion '" + criterion + "'. Allowed: " + CRITERIA);
        }
        LocalDateTime start = from == null ? LocalDateTime.of(1970, 1, 1, 0, 0) : from.atStartOfDay();
        LocalDateTime end = to == null ? LocalDateTime.now().plusDays(1) : to.plusDays(1).atStartOfDay().minusNanos(1);
        if (start.isAfter(end)) {
            throw new BadRequestException("'from' must not be after 'to'");
        }
        return snapshots.findByUserIdAndRecordedAtBetweenOrderByRecordedAtAsc(userId, start, end).stream()
                .map(s -> new HistoryPoint(s.getRecordedAt(), s.getEstimatedLevel(), pick(s, wanted)))
                .toList();
    }

    public GapResponse getGap(Long userId) {
        ProgressSnapshot latest = snapshots.findFirstByUserIdOrderByRecordedAtDesc(userId)
                .orElseThrow(() -> new NotFoundException("No progress found for user " + userId));
        String target = userClient.getTargetLevel(userId);
        int remaining = Math.max(0, Levels.rank(target) - Levels.rank(latest.getEstimatedLevel()));
        String weakest = CRITERIA.stream()
                .min(Comparator.comparingDouble(c -> score(latest, c)))
                .orElse(null);
        return new GapResponse(userId, target, latest.getEstimatedLevel(), remaining, remaining == 0, weakest);
    }

    public DashboardResponse getDashboard(Long groupId) {
        List<Long> members = userClient.getGroupMembers(groupId);
        if (members == null || members.isEmpty()) {
            throw new NotFoundException("Group " + groupId + " not found or has no members");
        }
        List<MemberRow> rows = members.stream()
                .map(id -> snapshots.findFirstByUserIdOrderByRecordedAtDesc(id)
                        .map(s -> new MemberRow(id, s.getEstimatedLevel(), average(s), s.getRecordedAt()))
                        .orElse(new MemberRow(id, null, 0, null)))
                .toList();
        long open = alerts.countByGroupIdAndStatus(groupId, AlertStatus.OPEN);
        return new DashboardResponse(groupId, rows.size(), rows, open);
    }

    // ---- helpers -------------------------------------------------------------------------

    public static double score(ProgressSnapshot s, String criterion) {
        return switch (criterion) {
            case "pronunciation" -> s.getPronunciation();
            case "fluency" -> s.getFluency();
            case "intonation" -> s.getIntonation();
            case "lexicon" -> s.getLexicon();
            case "grammar" -> s.getGrammar();
            case "coherence" -> s.getCoherence();
            default -> throw new IllegalArgumentException(criterion);
        };
    }

    public static double average(ProgressSnapshot s) {
        double avg = CRITERIA.stream().mapToDouble(c -> score(s, c)).average().orElse(0);
        return Math.round(avg * 100.0) / 100.0;
    }

    private static Map<String, Double> pick(ProgressSnapshot s, String criterion) {
        Map<String, Double> map = new LinkedHashMap<>();
        for (String c : CRITERIA) {
            if (criterion == null || c.equals(criterion)) {
                map.put(c, score(s, c));
            }
        }
        return map;
    }

    public static SnapshotResponse toResponse(ProgressSnapshot s) {
        Scores scores = new Scores(s.getPronunciation(), s.getFluency(), s.getIntonation(),
                s.getLexicon(), s.getGrammar(), s.getCoherence());
        return new SnapshotResponse(s.getId(), s.getRecordedAt(), s.getSource(), scores, s.getEstimatedLevel());
    }
}
