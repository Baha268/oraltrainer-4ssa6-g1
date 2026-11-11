package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.dto.ModuleRequestDTO;
import com.oraltrainer.content.dto.ModuleResponseDTO;

import java.util.List;

public interface IModuleService {
    ModuleResponseDTO createModule(ModuleRequestDTO requestDTO);
    ModuleResponseDTO getModuleById(Long id);
    List<ModuleResponseDTO> getModulesByCourseId(Long courseId);
    ModuleResponseDTO updateModule(Long id, ModuleRequestDTO requestDTO);
    void deleteModule(Long id);
}
