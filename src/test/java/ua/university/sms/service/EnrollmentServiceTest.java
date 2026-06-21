package ua.university.sms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.university.sms.model.dto.EnrollmentRequestDto;
import ua.university.sms.model.dto.EnrollmentResponseDto;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Student;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseRepository courseRepository;

    @InjectMocks private EnrollmentService service;

    @Test
    void createEnrollment_ValidData_ReturnsDto() {
        Student student = new Student(); student.setId(1L);
        Course course = new Course(); course.setId(2L);
        EnrollmentRequestDto dto = new EnrollmentRequestDto(1L, 2L, "Spring", 2026);

        Enrollment saved = new Enrollment();
        saved.setId(5L); saved.setStudent(student); saved.setCourse(course);
        saved.setSemester("Spring"); saved.setYear(2026);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(saved);

        EnrollmentResponseDto result = service.createEnrollment(dto);

        assertNotNull(result);
        assertEquals(5L, result.id());
        assertEquals(1L, result.studentId());
        assertEquals(2L, result.courseId());
    }
}