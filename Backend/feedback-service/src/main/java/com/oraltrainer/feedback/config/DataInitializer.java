package com.oraltrainer.feedback.config;

import com.oraltrainer.feedback.entity.Category;
import com.oraltrainer.feedback.entity.Correction;
import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.entity.FeedbackStatus;
import com.oraltrainer.feedback.entity.RecommendedExercise;
import com.oraltrainer.feedback.repository.FeedbackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Données de démonstration insérées au démarrage (base vide uniquement),
 * pour pouvoir tester les endpoints dans Swagger avant l'arrivée des événements RabbitMQ.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final FeedbackRepository feedbackRepository;

    public DataInitializer(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) {
        if (feedbackRepository.count() > 0) {
            return;
        }

        // Feedback 1 : apprenant 7, anglais
        Feedback f1 = newFeedback(42L, 7L, "EN", 72.5, 64.0, 58.3, 61.0, 55.5, 70.0,
                "Clear ideas overall. Watch subject-verb agreement and use more precise vocabulary.");
        f1.addCorrection(new Correction(Category.GRAMMAR, "She go to work by bus",
                "She goes to work by bus", "Third person singular takes -s in the present simple.", 1));
        f1.addCorrection(new Correction(Category.LEXICON, "I did a mistake",
                "I made a mistake", "The collocation is 'make a mistake'.", 2));
        f1.addCorrection(new Correction(Category.COHERENCE, "And also, so, the city is nice",
                "In addition, the city is pleasant", "Use a single linking word to introduce the idea.", 3));
        f1.addRecommendedExercise(new RecommendedExercise(12L, "Accord sujet-verbe au présent simple"));
        f1.addRecommendedExercise(new RecommendedExercise(18L, "Collocations make / do"));

        // Feedback 2 : apprenant 7, français
        Feedback f2 = newFeedback(43L, 7L, "FR", 68.0, 71.5, 66.0, 74.0, 62.0, 69.5,
                "Discours fluide. Revoir l'accord du participe passé avec être.");
        f2.addCorrection(new Correction(Category.GRAMMAR, "elles sont allé au marché",
                "elles sont allées au marché", "Avec être, le participe passé s'accorde avec le sujet.", 1));
        f2.addRecommendedExercise(new RecommendedExercise(25L, "Accord du participe passé"));

        // Feedback 3 : apprenant 8, français
        Feedback f3 = newFeedback(44L, 8L, "FR", 80.0, 77.0, 75.5, 70.0, 73.0, 78.0,
                "Très bonne structure, enrichir le vocabulaire argumentatif.");
        f3.addCorrection(new Correction(Category.LEXICON, "c'est une chose importante",
                "c'est un enjeu majeur", "Préférer un terme plus précis à l'oral formel.", 1));
        f3.addRecommendedExercise(new RecommendedExercise(31L, "Connecteurs et vocabulaire argumentatif"));

        feedbackRepository.save(f1);
        feedbackRepository.save(f2);
        feedbackRepository.save(f3);
    }

    private static Feedback newFeedback(Long recordingId, Long userId, String language,
                                        double pronunciation, double fluency, double intonation,
                                        double lexicon, double grammar, double coherence,
                                        String globalComment) {
        Feedback f = new Feedback();
        f.setRecordingId(recordingId);
        f.setUserId(userId);
        f.setLanguage(language);
        f.setPronunciation(pronunciation);
        f.setFluency(fluency);
        f.setIntonation(intonation);
        f.setLexicon(lexicon);
        f.setGrammar(grammar);
        f.setCoherence(coherence);
        f.setGlobalComment(globalComment);
        f.setStatus(FeedbackStatus.GENERATED);
        return f;
    }
}
