package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.entity.Course;
import com.oraltrainer.content.repository.CourseRepository;
import com.oraltrainer.content.service.interfaces.ICourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByLanguageAndLevel(String language, String level) {
        if (language != null && level != null) {
            return courseRepository.findByLanguageIgnoreCaseAndLevelIgnoreCase(language, level);
        } else if (language != null) {
            return courseRepository.findByLanguageIgnoreCase(language);
        }
        return courseRepository.findAll();
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setTitle(courseDetails.getTitle());
            course.setDescription(courseDetails.getDescription());
            course.setLanguage(courseDetails.getLanguage());
            course.setLevel(courseDetails.getLevel());
            return courseRepository.save(course);
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
