package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.EnrollmentRequestDto;
import ua.university.sms.model.dto.EnrollmentResponseDto;
import ua.university.sms.model.entity.*;
import ua.university.sms.repository.*;
import ua.university.sms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class EnrollmentService implements Payable {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<EnrollmentResponseDto> getAll() {
        return enrollmentRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public EnrollmentResponseDto getById(Long id) {
        return enrollmentRepository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
    }

    @Transactional
    public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + dto.studentId()));
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.courseId()));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(dto.semester());
        enrollment.setYear(dto.year());
        return mapToDto(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public EnrollmentResponseDto updateGrade(Long id, Grade grade) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        enrollment.setGrade(grade);
        return mapToDto(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        enrollment.setPaid(true);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void delete(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment not found with id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentResponseDto mapToDto(Enrollment e) {
        return new EnrollmentResponseDto(e.getId(), e.getStudent().getId(), e.getCourse().getId(), 
                                         e.getSemester(), e.getYear(), e.getPaid(), e.getGrade());
    }
}