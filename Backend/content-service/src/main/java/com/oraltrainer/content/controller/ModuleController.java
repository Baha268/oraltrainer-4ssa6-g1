package com.oraltrainer.content.controller;

import com.oraltrainer.content.dto.ModuleRequestDTO;
import com.oraltrainer.content.dto.ModuleResponseDTO;
import com.oraltrainer.content.service.interfaces.IModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Module Controller", description = "Gestion des modules pédagogiques à l'intérieur des cours")
@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final IModuleService moduleService;

    public ModuleController(IModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Operation(summary = "Créer un nouveau module")
    @ApiResponse(responseCode = "201", description = "Module créé avec succès")
    @ApiResponse(responseCode = "404", description = "Cours parent non trouvé")
    @PostMapping
    public ResponseEntity<ModuleResponseDTO> createModule(@Valid @RequestBody ModuleRequestDTO requestDTO) {
        ModuleResponseDTO response = moduleService.createModule(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un module par son ID")
    @ApiResponse(responseCode = "200", description = "Module trouvé")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponseDTO> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    @Operation(summary = "Lister les modules d'un cours donné")
    @ApiResponse(responseCode = "200", description = "Liste des modules du cours")
    @ApiResponse(responseCode = "404", description = "Cours non trouvé")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ModuleResponseDTO>> getModulesByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(moduleService.getModulesByCourseId(courseId));
    }

    @Operation(summary = "Modifier un module existant")
    @ApiResponse(responseCode = "200", description = "Module mis à jour")
    @ApiResponse(responseCode = "404", description = "Module ou cours non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponseDTO> updateModule(
            @PathVariable Long id,
            @Valid @RequestBody ModuleRequestDTO requestDTO) {
        return ResponseEntity.ok(moduleService.updateModule(id, requestDTO));
    }

    @Operation(summary = "Supprimer un module par son ID")
    @ApiResponse(responseCode = "204", description = "Module supprimé")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
