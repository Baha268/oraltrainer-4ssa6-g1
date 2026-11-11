package com.oraltrainer.content.controller;

import com.oraltrainer.content.entity.Exercise;
import com.oraltrainer.content.service.interfaces.IExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Exercise Controller", description = "Gestion des exercices ciblés par thématique d'erreur et niveau")
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final IExerciseService exerciseService;

    public ExerciseController(IExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Operation(summary = "Créer un nouvel exercice")
    @ApiResponse(responseCode = "201", description = "Exercice créé avec succès")
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise created = exerciseService.createExercise(exercise);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un exercice par son ID")
    @ApiResponse(responseCode = "200", description = "Exercice trouvé")
    @ApiResponse(responseCode = "404", description = "Exercice non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExerciseById(id);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exercise);
    }

    @Operation(summary = "Lister les exercices d'une leçon donnée")
    @ApiResponse(responseCode = "200", description = "Liste des exercices de la leçon")
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Exercise>> getExercisesByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(exerciseService.getExercisesByLessonId(lessonId));
    }

    @Operation(summary = "Lister les exercices filtrés par thématique d'erreur et niveau (utilisé par feedback-service)")
    @ApiResponse(responseCode = "200", description = "Liste des exercices ciblés")
    @GetMapping
    public ResponseEntity<List<Exercise>> getExercises(
            @Parameter(description = "Thématique d'erreur (ex: PRONUNCIATION, GRAMMAR)") @RequestParam(required = false) String tag,
            @Parameter(description = "Niveau CECRL (ex: B1, B2)") @RequestParam(required = false) String level) {
        return ResponseEntity.ok(exerciseService.getExercisesByErrorTagAndLevel(tag, level));
    }

    @Operation(summary = "Modifier un exercice existant")
    @ApiResponse(responseCode = "200", description = "Exercice mis à jour")
    @ApiResponse(responseCode = "404", description = "Exercice non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        Exercise updated = exerciseService.updateExercise(id, exercise);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer un exercice par son ID")
    @ApiResponse(responseCode = "204", description = "Exercice supprimé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
