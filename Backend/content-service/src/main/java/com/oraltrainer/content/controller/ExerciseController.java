package com.oraltrainer.content.controller;

import com.oraltrainer.content.dto.ExerciseRequestDTO;
import com.oraltrainer.content.dto.ExerciseResponseDTO;
import com.oraltrainer.content.service.interfaces.IExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @ApiResponse(responseCode = "404", description = "Leçon parente non trouvée")
    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> createExercise(@Valid @RequestBody ExerciseRequestDTO requestDTO) {
        ExerciseResponseDTO response = exerciseService.createExercise(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un exercice par son ID")
    @ApiResponse(responseCode = "200", description = "Exercice trouvé")
    @ApiResponse(responseCode = "404", description = "Exercice non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @Operation(summary = "Lister les exercices d'une leçon donnée")
    @ApiResponse(responseCode = "200", description = "Liste des exercices de la leçon")
    @ApiResponse(responseCode = "404", description = "Leçon non trouvée")
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<ExerciseResponseDTO>> getExercisesByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(exerciseService.getExercisesByLessonId(lessonId));
    }

    @Operation(summary = "Lister les exercices filtrés par thématique d'erreur et niveau (utilisé par feedback-service)")
    @ApiResponse(responseCode = "200", description = "Liste des exercices ciblés")
    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> getExercises(
            @Parameter(description = "Thématique d'erreur (ex: PRONUNCIATION, GRAMMAR)") @RequestParam(required = false) String tag,
            @Parameter(description = "Niveau CECRL (ex: B1, B2)") @RequestParam(required = false) String level) {
        return ResponseEntity.ok(exerciseService.getExercisesByErrorTagAndLevel(tag, level));
    }

    @Operation(summary = "Modifier un exercice existant")
    @ApiResponse(responseCode = "200", description = "Exercice mis à jour")
    @ApiResponse(responseCode = "404", description = "Exercice ou leçon non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody ExerciseRequestDTO requestDTO) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, requestDTO));
    }

    @Operation(summary = "Supprimer un exercice par son ID")
    @ApiResponse(responseCode = "204", description = "Exercice supprimé")
    @ApiResponse(responseCode = "404", description = "Exercice non trouvé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
