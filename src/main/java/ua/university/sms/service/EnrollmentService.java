package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.EnrollmentRequestDto;
import ua.university.sms.model.entity.*;
import ua.university.sms.repository.*;
import ua.university.sms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class EnrollmentService implements Payable {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                             StudentRepository studentRepository, 
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Enrollment createEnrollment(EnrollmentRequestDto dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(dto.semester());
        enrollment.setYear(dto.year());
        enrollment.setPaid(false);
        enrollment.setGrade(Grade.NA);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void updateGrade(Long id, Grade grade) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollment.setGrade(grade);
        enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollment.setPaid(true);
        enrollmentRepository.save(enrollment);
    }

    public double calculateStudentGPA(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        List<Enrollment> gradedEnrollments = student.getEnrollments().stream()
                .filter(e -> e.getGrade() != Grade.NA)
                .toList();

        if (gradedEnrollments.isEmpty()) return 0.0;

        double totalPoints = gradedEnrollments.stream()
                .mapToDouble(e -> e.getGrade().getGpaValue() * e.getCourse().getCredits())
                .sum();

        int totalCredits = gradedEnrollments.stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}