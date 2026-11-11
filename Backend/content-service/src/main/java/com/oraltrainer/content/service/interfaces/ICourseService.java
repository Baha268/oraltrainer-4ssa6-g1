package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.entity.Course;
import java.util.List;

public interface ICourseService {
    Course createCourse(Course course);
    Course getCourseById(Long id);
    List<Course> getAllCourses();
    List<Course> getCoursesByLanguageAndLevel(String language, String level);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}
