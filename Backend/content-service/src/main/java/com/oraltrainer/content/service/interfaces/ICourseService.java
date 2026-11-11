package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.dto.CourseRequestDTO;
import com.oraltrainer.content.dto.CourseResponseDTO;

import java.util.List;

public interface ICourseService {
    CourseResponseDTO createCourse(CourseRequestDTO requestDTO);
    CourseResponseDTO getCourseById(Long id);
    List<CourseResponseDTO> getAllCourses();
    List<CourseResponseDTO> getCoursesByLanguageAndLevel(String language, String level);
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO);
    void deleteCourse(Long id);
}
