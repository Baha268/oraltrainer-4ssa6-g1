package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.dto.CourseRequestDTO;
import com.oraltrainer.content.dto.CourseResponseDTO;
import com.oraltrainer.content.entity.Course;
import com.oraltrainer.content.exception.ResourceNotFoundException;
import com.oraltrainer.content.mapper.CourseMapper;
import com.oraltrainer.content.repository.CourseRepository;
import com.oraltrainer.content.service.interfaces.ICourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO) {
        Course course = courseMapper.toEntity(requestDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDTO(savedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));
        return courseMapper.toResponseDTO(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCoursesByLanguageAndLevel(String language, String level) {
        List<Course> courses;
        if (language != null && level != null) {
            courses = courseRepository.findByLanguageIgnoreCaseAndLevelIgnoreCase(language, level);
        } else if (language != null) {
            courses = courseRepository.findByLanguageIgnoreCase(language);
        } else {
            courses = courseRepository.findAll();
        }
        return courses.stream()
                .map(courseMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));
        courseMapper.updateEntityFromDto(requestDTO, course);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponseDTO(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cours non trouvé avec l'id : " + id);
        }
        courseRepository.deleteById(id);
    }
}
