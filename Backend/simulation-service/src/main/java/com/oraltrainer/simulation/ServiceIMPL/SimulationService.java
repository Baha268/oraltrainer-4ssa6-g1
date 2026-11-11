package com.oraltrainer.simulation.ServiceIMPL;

import com.oraltrainer.simulation.Entity.*;
import com.oraltrainer.simulation.ServiceInterface.ISimulationService;
import com.oraltrainer.simulation.repository.SimulationRepository;
import com.oraltrainer.simulation.repository.SimulationStepRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Orchestre une épreuve blanche : PREPARATION -> MONOLOGUE -> INTERACTION.
 *
 * TODO (séances 5 à 7) : appels OpenFeign à user-service et content-service au démarrage
 * (503 si indisponibles) ; écoute de RecordingTranscribed (choix de la relance) ;
 * écoute de FeedbackGenerated / FeedbackValidated (calcul de la note) ;
 * publication de SimulationFinished.
 */
@Service
@AllArgsConstructor
@Transactional
public class SimulationService implements ISimulationService {

    private static final int NB_ETAPES = StepType.values().length;
    private static final int DUREE_PREPARATION = 120;
    private static final int DUREE_MONOLOGUE = 120;
    private static final int DUREE_INTERACTION = 180;

    SimulationRepository simulationRepository;
    SimulationStepRepository stepRepository;

    @Override
    public Simulation creer(Simulation simulation) {
        // L'état initial est imposé par le service, pas par le client
        simulation.setIdSimulation(null);
        simulation.setStatus(SimulationStatus.CREATED);
        simulation.setCreatedAt(LocalDateTime.now());
        simulation.setSteps(new ArrayList<>());
        simulation.setEstimatedScores(new ArrayList<>());
        // TODO : subjectId et gridId viennent de content-service (OpenFeign)
        return simulationRepository.save(simulation);
    }

    @Override
    @Transactional(readOnly = true)
    public Simulation obtenirParId(Long id) {
        return simulationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Simulation " + id + " introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Simulation> listerParUtilisateur(Long userId, Pageable pageable) {
        return simulationRepository.findByUserId(userId, pageable);
    }

    @Override
    public SimulationStep demarrerEtapeSuivante(Long idSimulation) {
        Simulation s = obtenirParId(idSimulation);
        if (s.getStatus() == SimulationStatus.WAITING_ANALYSIS || s.getStatus() == SimulationStatus.FINISHED) {
            throw conflit("L'épreuve est déjà clôturée");
        }
        int nb = s.getSteps().size();
        if (nb >= NB_ETAPES) {
            throw conflit("Les trois étapes ont déjà été démarrées");
        }
        if (nb > 0) {
            SimulationStep derniere = s.getSteps().get(nb - 1);
            if (derniere.getStatus() == StepStatus.RUNNING) {
                if (derniere.getType() != StepType.PREPARATION) {
                    throw conflit("L'étape " + derniere.getIdStep() + " est en cours : associez d'abord son enregistrement");
                }
                derniere.setStatus(StepStatus.DONE);
                derniere.setEndedAt(LocalDateTime.now());
            }
        }
        StepType type = StepType.values()[nb];
        SimulationStep step = new SimulationStep();
        step.setSimulation(s);
        step.setType(type);
        step.setOrderIndex(nb + 1);
        step.setDurationSec(dureeDe(type));
        s.getSteps().add(step);
        s.setStatus(SimulationStatus.IN_PROGRESS);
        simulationRepository.saveAndFlush(s);
        return step;
    }

    @Override
    public SimulationStep associerEnregistrement(Long idSimulation, Long idStep, Long recordingId) {
        SimulationStep step = trouverEtape(idSimulation, idStep);
        if (step.getType() == StepType.PREPARATION) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'étape de préparation n'a pas d'enregistrement");
        }
        if (step.getRecordingId() != null) {
            throw conflit("Un enregistrement est déjà associé à l'étape " + idStep);
        }
        step.setRecordingId(recordingId);
        step.setStatus(StepStatus.DONE);
        step.setEndedAt(LocalDateTime.now());
        return stepRepository.save(step);
    }

    @Override
    @Transactional(readOnly = true)
    public ExaminerPrompt obtenirRelance(Long idSimulation, Long idStep) {
        SimulationStep step = trouverEtape(idSimulation, idStep);
        if (step.getType() != StepType.INTERACTION) {
            throw conflit("Seule l'étape INTERACTION a une relance de l'examinateur");
        }
        if (step.getPrompt() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas encore de relance pour l'étape " + idStep);
        }
        return step.getPrompt();
    }

    @Override
    public Simulation cloturer(Long idSimulation) {
        Simulation s = obtenirParId(idSimulation);
        boolean complete = s.getSteps().size() == NB_ETAPES
                && s.getSteps().stream().allMatch(st -> st.getStatus() == StepStatus.DONE);
        if (s.getStatus() != SimulationStatus.IN_PROGRESS || !complete) {
            throw conflit("L'épreuve ne peut pas être clôturée : les trois étapes doivent être terminées");
        }
        s.setStatus(SimulationStatus.WAITING_ANALYSIS);
        return simulationRepository.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Simulation obtenirResultat(Long idSimulation) {
        Simulation s = obtenirParId(idSimulation);
        if (s.getStatus() != SimulationStatus.FINISHED) {
            throw conflit("Résultat indisponible pour le moment (statut " + s.getStatus() + ")");
        }
        return s;
    }

    private SimulationStep trouverEtape(Long idSimulation, Long idStep) {
        Simulation s = obtenirParId(idSimulation);
        return s.getSteps().stream()
                .filter(st -> st.getIdStep().equals(idStep))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Étape " + idStep + " introuvable dans la simulation " + idSimulation));
    }

    private int dureeDe(StepType type) {
        return switch (type) {
            case PREPARATION -> DUREE_PREPARATION;
            case MONOLOGUE -> DUREE_MONOLOGUE;
            case INTERACTION -> DUREE_INTERACTION;
        };
    }

    private ResponseStatusException conflit(String message) {
        return new ResponseStatusException(HttpStatus.CONFLICT, message);
    }
}
