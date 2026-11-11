package com.oraltrainer.simulation.Controller;

import com.oraltrainer.simulation.Entity.ExaminerPrompt;
import com.oraltrainer.simulation.Entity.Simulation;
import com.oraltrainer.simulation.Entity.SimulationStep;
import com.oraltrainer.simulation.ServiceInterface.ISimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/simulations")
@Tag(name = "gestion Simulation")
public class SimulationController {
    ISimulationService simulationService;

    @Operation(description = "cette méthode permet de démarrer une nouvelle épreuve blanche")
    @PostMapping
    public ResponseEntity<Simulation> creer(@Valid @RequestBody Simulation simulation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(simulationService.creer(simulation));
    }

    @Operation(description = "cette méthode permet de récupérer l'état d'une épreuve et de ses étapes")
    @GetMapping("/{id}")
    public Simulation obtenirParId(@PathVariable Long id) {
        return simulationService.obtenirParId(id);
    }

    @Operation(description = "cette méthode permet de récupérer l'historique des épreuves d'un apprenant")
    @GetMapping
    public Page<Simulation> listerParUtilisateur(@RequestParam Long userId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return simulationService.listerParUtilisateur(userId, PageRequest.of(page, size));
    }

    @Operation(description = "cette méthode permet de démarrer l'étape suivante (chronomètre)")
    @PostMapping("/{id}/steps")
    public ResponseEntity<SimulationStep> demarrerEtapeSuivante(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(simulationService.demarrerEtapeSuivante(id));
    }

    @Operation(description = "cette méthode permet d'associer l'enregistrement de l'apprenant à une étape")
    @PutMapping("/{id}/steps/{stepId}/recording")
    public SimulationStep associerEnregistrement(@PathVariable Long id, @PathVariable Long stepId,
                                                 @RequestBody Map<String, Long> body) {
        Long recordingId = body.get("recordingId");
        if (recordingId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recordingId est obligatoire");
        }
        return simulationService.associerEnregistrement(id, stepId, recordingId);
    }

    @Operation(description = "cette méthode permet de récupérer la relance de l'examinateur")
    @GetMapping("/{id}/steps/{stepId}/prompt")
    public ExaminerPrompt obtenirRelance(@PathVariable Long id, @PathVariable Long stepId) {
        return simulationService.obtenirRelance(id, stepId);
    }

    @Operation(description = "cette méthode permet de clôturer l'épreuve et de lancer le calcul de la note")
    @PostMapping("/{id}/finish")
    public ResponseEntity<Simulation> cloturer(@PathVariable Long id) {
        return ResponseEntity.accepted().body(simulationService.cloturer(id));
    }

    @Operation(description = "cette méthode permet de récupérer la note estimée et le bilan")
    @GetMapping("/{id}/result")
    public Simulation obtenirResultat(@PathVariable Long id) {
        return simulationService.obtenirResultat(id);
    }
}
