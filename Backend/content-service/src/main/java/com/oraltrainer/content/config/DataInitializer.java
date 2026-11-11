package com.oraltrainer.content.config;

import com.oraltrainer.content.entity.Course;
import com.oraltrainer.content.entity.Exercise;
import com.oraltrainer.content.entity.Lesson;
import com.oraltrainer.content.entity.Module;
import com.oraltrainer.content.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CourseRepository courseRepository;

    public DataInitializer(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (courseRepository.count() > 0) {
            log.info("Des données existent déjà en base. Initialisation ignorée.");
            return;
        }

        log.info("Initialisation des données pédagogiques de démonstration...");

        // ==========================================
        // 1. Cours Français : Préparation DELF B2
        // ==========================================
        Course delfCourse = new Course(
                "Préparation DELF B2 - Expression Orale",
                "Entraînement complet à l'épreuve orale du DELF B2 : monologue suivi et débat avec l'examinateur.",
                "FRENCH",
                "B2"
        );

        // Module 1 : Structuration de l'exposé
        Module module1 = new Module(
                "Module 1 : Structuration de l'exposé",
                "Apprendre à dégager une problématique et bâtir un plan en 3 parties.",
                1,
                delfCourse
        );

        Lesson lesson1 = new Lesson(
                "Leçon 1 : L'introduction et la problématique",
                "Techniques pour accrocher l'auditoire, poser le sujet et annoncer son plan.",
                15,
                1,
                module1
        );
        lesson1.addExercise(new Exercise(
                "Formuler une problématique percutante",
                "À partir d'un court extrait d'article, enregistrez votre problématique en 1 minute.",
                "COHERENCE",
                "B2",
                20,
                lesson1
        ));
        lesson1.addExercise(new Exercise(
                "Articulation des connecteurs logiques",
                "Prononcez distinctement : 'néanmoins', 'par conséquent', 'en outre', 'd'ailleurs'.",
                "PRONUNCIATION",
                "B2",
                20,
                lesson1
        ));
        module1.addLesson(lesson1);

        Lesson lesson2 = new Lesson(
                "Leçon 2 : Développer et illustrer ses arguments",
                "Comment soutenir chaque argument avec un exemple précis sans hésitation.",
                20,
                2,
                module1
        );
        lesson2.addExercise(new Exercise(
                "Enchaînement d'arguments contrastés",
                "Présentez les avantages et inconvénients du télétravail pendant 2 minutes.",
                "VOCABULARY",
                "B2",
                20,
                lesson2
        ));
        module1.addLesson(lesson2);
        delfCourse.addModule(module1);

        // Module 2 : Débat avec l'examinateur
        Module module2 = new Module(
                "Module 2 : Débat et relances de l'examinateur",
                "Techniques pour réagir aux objections et défendre son point de vue avec calme.",
                2,
                delfCourse
        );
        Lesson lesson3 = new Lesson(
                "Leçon 3 : Répondre aux questions pièges",
                "Stratégies pour reformuler une question et temporiser élégamment.",
                15,
                1,
                module2
        );
        lesson3.addExercise(new Exercise(
                "Gestion du débit et intonation face aux relances",
                "Répondez à 3 relances imprévues de l'examinateur virtuel en gardant une intonation posée.",
                "INTONATION",
                "B2",
                20,
                lesson3
        ));
        module2.addLesson(lesson3);
        delfCourse.addModule(module2);

        // ==========================================
        // 2. Cours Anglais : IELTS Speaking Mastery
        // ==========================================
        Course ieltsCourse = new Course(
                "IELTS Speaking Mastery - Band 7+",
                "Comprehensive preparation for IELTS Speaking: Fluency, Coherence, Lexical Resource, and Pronunciation.",
                "ENGLISH",
                "C1"
        );

        Module module3 = new Module(
                "Module 1: The Long Turn (Part 2)",
                "Master the 2-minute uninterrupted speech using the cue card method.",
                1,
                ieltsCourse
        );
        Lesson lesson4 = new Lesson(
                "Lesson 1: Eliminating Speech Hesitations",
                "Techniques to eliminate filler words (um, uh, like) and maintain a natural rhythm.",
                20,
                1,
                module3
        );
        lesson4.addExercise(new Exercise(
                "2-Minute Cue Card Speech on Technology",
                "Speak about an impactful technological innovation without pausing for more than 3 seconds.",
                "FLUENCY",
                "C1",
                20,
                lesson4
        ));
        lesson4.addExercise(new Exercise(
                "Idiomatic Phrasal Verbs Practice",
                "Use 5 idiomatic expressions accurately in a short spoken response.",
                "VOCABULARY",
                "C1",
                20,
                lesson4
        ));
        module3.addLesson(lesson4);
        ieltsCourse.addModule(module3);

        courseRepository.save(delfCourse);
        courseRepository.save(ieltsCourse);

        log.info("Données initialisées avec succès : 2 cours, 3 modules, 4 leçons, 6 exercices.");
    }
}
