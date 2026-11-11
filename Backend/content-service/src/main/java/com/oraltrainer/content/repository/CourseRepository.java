package com.oraltrainer.content.repository;

import com.oraltrainer.content.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLanguageIgnoreCase(String language);
    List<Course> findByLanguageIgnoreCaseAndLevelIgnoreCase(String language, String level);
}
