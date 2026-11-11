package com.esprit.oral.progress.config;

import com.esprit.oral.progress.entity.Alert;
import com.esprit.oral.progress.entity.AlertType;
import com.esprit.oral.progress.entity.ProgressSnapshot;
import com.esprit.oral.progress.entity.Severity;
import com.esprit.oral.progress.repository.AlertRepository;
import com.esprit.oral.progress.repository.ProgressSnapshotRepository;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Demo data so Swagger returns real results. Only runs when the database is empty. */
@Configuration
public class SeedData {

    @Bean
    CommandLineRunner seed(ProgressSnapshotRepository snapshots, AlertRepository alerts) {
        return args -> {
            if (snapshots.count() > 0) return;
            String[] levels = {"B1", "B1", "B2", "B2", "B2", "C1"};
            ProgressSnapshot last = null;
            for (long user = 1; user <= 3; user++) {
                for (int i = 0; i < 6; i++) {
                    ProgressSnapshot s = new ProgressSnapshot();
                    s.setUserId(user);
                    s.setRecordedAt(LocalDateTime.now().minusWeeks(6 - i));
                    s.setSource("FEEDBACK");
                    double base = 8 + i * 1.6 + user * 0.5;
                    s.setPronunciation(Math.min(20, base));
                    s.setFluency(Math.min(20, base + 0.5));
                    s.setIntonation(Math.min(20, base - 0.5));
                    s.setLexicon(Math.min(20, base + 1));
                    s.setGrammar(Math.min(20, base - 1));
                    s.setCoherence(Math.min(20, base));
                    s.setEstimatedLevel(user == 3 && i > 2 ? "B1" : levels[i]);
                    s.setEventId("seed-" + user + "-" + i);
                    last = snapshots.save(s);
                }
            }
            Alert a = new Alert();
            a.setUserId(3L);
            a.setGroupId(1L);
            a.setType(AlertType.STAGNATION);
            a.setMessage("Scores have been stable over the last 3 evaluations");
            a.setSeverity(Severity.WARNING);
            a.setSnapshot(last);
            alerts.save(a);

            Alert b = new Alert();
            b.setUserId(2L);
            b.setGroupId(1L);
            b.setType(AlertType.INACTIVITY);
            b.setMessage("No activity for 14 days");
            b.setSeverity(Severity.INFO);
            alerts.save(b);
        };
    }
}
