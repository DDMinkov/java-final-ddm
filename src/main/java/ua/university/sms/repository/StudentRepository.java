package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.university.sms.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}