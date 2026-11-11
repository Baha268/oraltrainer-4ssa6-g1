package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.dto.ModuleRequestDTO;
import com.oraltrainer.content.dto.ModuleResponseDTO;
import com.oraltrainer.content.entity.Course;
import com.oraltrainer.content.entity.Module;
import com.oraltrainer.content.exception.ResourceNotFoundException;
import com.oraltrainer.content.mapper.ModuleMapper;
import com.oraltrainer.content.repository.CourseRepository;
import com.oraltrainer.content.repository.ModuleRepository;
import com.oraltrainer.content.service.interfaces.IModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleMapper moduleMapper;

    public ModuleServiceImpl(ModuleRepository moduleRepository, CourseRepository courseRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public ModuleResponseDTO createModule(ModuleRequestDTO requestDTO) {
        Course course = courseRepository.findById(requestDTO.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + requestDTO.courseId()));
        Module module = moduleMapper.toEntity(requestDTO, course);
        Module savedModule = moduleRepository.save(module);
        return moduleMapper.toResponseDTO(savedModule);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleResponseDTO getModuleById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'id : " + id));
        return moduleMapper.toResponseDTO(module);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleResponseDTO> getModulesByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Cours non trouvé avec l'id : " + courseId);
        }
        return moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream()
                .map(moduleMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ModuleResponseDTO updateModule(Long id, ModuleRequestDTO requestDTO) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'id : " + id));
        Course course = null;
        if (requestDTO.courseId() != null && !requestDTO.courseId().equals(module.getCourse().getId())) {
            course = courseRepository.findById(requestDTO.courseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + requestDTO.courseId()));
        }
        moduleMapper.updateEntityFromDto(requestDTO, module, course);
        Module updatedModule = moduleRepository.save(module);
        return moduleMapper.toResponseDTO(updatedModule);
    }

    @Override
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Module non trouvé avec l'id : " + id);
        }
        moduleRepository.deleteById(id);
    }
}
