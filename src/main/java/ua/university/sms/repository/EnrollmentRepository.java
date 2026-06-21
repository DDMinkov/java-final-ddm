package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.university.sms.model.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}