package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.university.sms.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}