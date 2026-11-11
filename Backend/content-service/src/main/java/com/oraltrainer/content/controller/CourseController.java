package com.oraltrainer.content.controller;

import com.oraltrainer.content.dto.CourseRequestDTO;
import com.oraltrainer.content.dto.CourseResponseDTO;
import com.oraltrainer.content.service.interfaces.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course Controller", description = "Gestion des cours d'expression orale et parcours d'apprentissage")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Créer un nouveau cours")
    @ApiResponse(responseCode = "201", description = "Cours créé avec succès")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO requestDTO) {
        CourseResponseDTO response = courseService.createCourse(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un cours par son ID")
    @ApiResponse(responseCode = "200", description = "Cours trouvé")
    @ApiResponse(responseCode = "404", description = "Cours non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(summary = "Lister tous les cours avec filtres optionnels par langue et niveau")
    @ApiResponse(responseCode = "200", description = "Liste des cours")
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getCourses(
            @Parameter(description = "Langue (ex: FRENCH, ENGLISH)") @RequestParam(required = false) String language,
            @Parameter(description = "Niveau CECRL (ex: B1, B2, C1)") @RequestParam(required = false) String level) {
        return ResponseEntity.ok(courseService.getCoursesByLanguageAndLevel(language, level));
    }

    @Operation(summary = "Modifier un cours existant")
    @ApiResponse(responseCode = "200", description = "Cours mis à jour")
    @ApiResponse(responseCode = "404", description = "Cours non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDTO requestDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDTO));
    }

    @Operation(summary = "Supprimer un cours par son ID")
    @ApiResponse(responseCode = "204", description = "Cours supprimé")
    @ApiResponse(responseCode = "404", description = "Cours non trouvé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
