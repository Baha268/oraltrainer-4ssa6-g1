package com.oraltrainer.content.controller;

import com.oraltrainer.content.entity.Lesson;
import com.oraltrainer.content.service.interfaces.ILessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        Lesson created = lessonService.createLesson(lesson);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer une leçon par son ID")
    @ApiResponse(responseCode = "200", description = "Leçon trouvée")
    @ApiResponse(responseCode = "404", description = "Leçon non trouvée")
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "Lister les leçons d'un module donné")
    @ApiResponse(responseCode = "200", description = "Liste des leçons du module")
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Lesson>> getLessonsByModuleId(@PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getLessonsByModuleId(moduleId));
    }

    @Operation(summary = "Modifier une leçon existante")
    @ApiResponse(responseCode = "200", description = "Leçon mise à jour")
    @ApiResponse(responseCode = "404", description = "Leçon non trouvée")
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        Lesson updated = lessonService.updateLesson(id, lesson);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer une leçon par son ID")
    @ApiResponse(responseCode = "204", description = "Leçon supprimée")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
