package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.entity.Lesson;
import java.util.List;

public interface ILessonService {
    Lesson createLesson(Lesson lesson);
    Lesson getLessonById(Long id);
    List<Lesson> getLessonsByModuleId(Long moduleId);
    Lesson updateLesson(Long id, Lesson lesson);
    void deleteLesson(Long id);
}
