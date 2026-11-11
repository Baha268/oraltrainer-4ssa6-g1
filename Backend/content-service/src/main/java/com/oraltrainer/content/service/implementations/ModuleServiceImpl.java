package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.entity.Module;
import com.oraltrainer.content.repository.ModuleRepository;
import com.oraltrainer.content.service.interfaces.IModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    @Transactional(readOnly = true)
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Module> getModulesByCourseId(Long courseId) {
        return moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
    }

    @Override
    public Module updateModule(Long id, Module moduleDetails) {
        Module module = moduleRepository.findById(id).orElse(null);
        if (module != null) {
            module.setTitle(moduleDetails.getTitle());
            module.setDescription(moduleDetails.getDescription());
            module.setOrderIndex(moduleDetails.getOrderIndex());
            if (moduleDetails.getCourse() != null) {
                module.setCourse(moduleDetails.getCourse());
            }
            return moduleRepository.save(module);
        }
        return null;
    }

    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
