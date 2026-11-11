package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.entity.Module;
import java.util.List;

public interface IModuleService {
    Module createModule(Module module);
    Module getModuleById(Long id);
    List<Module> getModulesByCourseId(Long courseId);
    Module updateModule(Long id, Module module);
    void deleteModule(Long id);
}
