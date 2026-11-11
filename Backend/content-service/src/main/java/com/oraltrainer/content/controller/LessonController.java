package com.oraltrainer.content.controller;

import com.oraltrainer.content.dto.LessonRequestDTO;
import com.oraltrainer.content.dto.LessonResponseDTO;
import com.oraltrainer.content.service.interfaces.ILessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lesson Controller", description = "Gestion des leçons au sein des modules")
@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final ILessonService lessonService;

    public LessonController(ILessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Créer une nouvelle leçon")
    @ApiResponse(responseCode = "201", description = "Leçon créée avec succès")
    @ApiResponse(responseCode = "404", description = "Module parent non trouvé")
    @PostMapping
    public ResponseEntity<LessonResponseDTO> createLesson(@Valid @RequestBody LessonRequestDTO requestDTO) {
        LessonResponseDTO response = lessonService.createLesson(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer une leçon par son ID")
    @ApiResponse(responseCode = "200", description = "Leçon trouvée")
    @ApiResponse(responseCode = "404", description = "Leçon non trouvée")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @Operation(summary = "Lister les leçons d'un module donné")
    @ApiResponse(responseCode = "200", description = "Liste des leçons du module")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<LessonResponseDTO>> getLessonsByModuleId(@PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getLessonsByModuleId(moduleId));
    }

    @Operation(summary = "Modifier une leçon existante")
    @ApiResponse(responseCode = "200", description = "Leçon mise à jour")
    @ApiResponse(responseCode = "404", description = "Leçon ou module non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody LessonRequestDTO requestDTO) {
        return ResponseEntity.ok(lessonService.updateLesson(id, requestDTO));
    }

    @Operation(summary = "Supprimer une leçon par son ID")
    @ApiResponse(responseCode = "204", description = "Leçon supprimée")
    @ApiResponse(responseCode = "404", description = "Leçon non trouvée")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
