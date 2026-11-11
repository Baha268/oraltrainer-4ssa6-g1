package com.oraltrainer.simulation.ServiceInterface;

import com.oraltrainer.simulation.Entity.ExaminerPrompt;
import com.oraltrainer.simulation.Entity.Simulation;
import com.oraltrainer.simulation.Entity.SimulationStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISimulationService {
    Simulation creer(Simulation simulation);
    Simulation obtenirParId(Long id);
    Page<Simulation> listerParUtilisateur(Long userId, Pageable pageable);
    SimulationStep demarrerEtapeSuivante(Long idSimulation);
    SimulationStep associerEnregistrement(Long idSimulation, Long idStep, Long recordingId);
    ExaminerPrompt obtenirRelance(Long idSimulation, Long idStep);
    Simulation cloturer(Long idSimulation);
    Simulation obtenirResultat(Long idSimulation);
}
