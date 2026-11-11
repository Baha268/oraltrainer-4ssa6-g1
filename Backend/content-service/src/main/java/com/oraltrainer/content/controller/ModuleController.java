package com.oraltrainer.content.controller;

import com.oraltrainer.content.entity.Module;
import com.oraltrainer.content.service.interfaces.IModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        Module created = moduleService.createModule(module);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un module par son ID")
    @ApiResponse(responseCode = "200", description = "Module trouvé")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        Module module = moduleService.getModuleById(id);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(module);
    }

    @Operation(summary = "Lister les modules d'un cours donné")
    @ApiResponse(responseCode = "200", description = "Liste des modules du cours")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Module>> getModulesByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(moduleService.getModulesByCourseId(courseId));
    }

    @Operation(summary = "Modifier un module existant")
    @ApiResponse(responseCode = "200", description = "Module mis à jour")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody Module module) {
        Module updated = moduleService.updateModule(id, module);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer un module par son ID")
    @ApiResponse(responseCode = "204", description = "Module supprimé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
