package com.esprit.oral.progress.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public final class ProgressDtos {

    private ProgressDtos() {}

    public record Scores(double pronunciation, double fluency, double intonation,
                         double lexicon, double grammar, double coherence) {}

    public record SnapshotResponse(Long id, LocalDateTime recordedAt, String source,
                                   Scores scores, String estimatedLevel) {}

    public record ProgressResponse(Long userId, int snapshotCount, SnapshotResponse latest,
                                   double averageScore) {}

    /** One point of the progress curves; "scores" only holds the requested criterion when one is given. */
    public record HistoryPoint(LocalDateTime recordedAt, String estimatedLevel,
                               Map<String, Double> scores) {}

    public record GapResponse(Long userId, String targetLevel, String estimatedLevel,
                              int levelsRemaining, boolean targetReached, String weakestCriterion) {}

    public record MemberRow(Long userId, String estimatedLevel, double averageScore,
                            LocalDateTime lastActivity) {}

    public record DashboardResponse(Long groupId, int memberCount, List<MemberRow> members,
                                    long openAlerts) {}
}
