package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.CourseRequestDto;
import ua.university.sms.model.dto.CourseResponseDto;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.TeacherRepository;
import ua.university.sms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<CourseResponseDto> getAll() {
        return courseRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public CourseResponseDto getById(Long id) {
        return courseRepository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Transactional
    public CourseResponseDto create(CourseRequestDto dto) {
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.teacherId()));
        Course course = new Course();
        course.setName(dto.name());
        course.setCredits(dto.credits());
        course.setDescription(dto.description());
        course.setTeacher(teacher);
        return mapToDto(courseRepository.save(course));
    }

    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + dto.teacherId()));
        course.setName(dto.name());
        course.setCredits(dto.credits());
        course.setDescription(dto.description());
        course.setTeacher(teacher);
        return mapToDto(courseRepository.save(course));
    }

    @Transactional
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    private CourseResponseDto mapToDto(Course course) {
        return new CourseResponseDto(course.getId(), course.getName(), course.getCredits(), 
                                     course.getDescription(), course.getTeacher() != null ? course.getTeacher().getId() : null);
    }
}