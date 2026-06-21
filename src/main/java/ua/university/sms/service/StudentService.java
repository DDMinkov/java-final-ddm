package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.StudentRequestDto;
import ua.university.sms.model.dto.StudentResponseDto;
import ua.university.sms.model.entity.Student;
import ua.university.sms.repository.StudentRepository;
import ua.university.sms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<StudentResponseDto> getAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    public StudentResponseDto getById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public StudentResponseDto create(StudentRequestDto dto) {
        Student student = new Student();
        updateEntity(student, dto);
        return mapToDto(repository.save(student));
    }

    @Transactional
    public StudentResponseDto update(Long id, StudentRequestDto dto) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        updateEntity(student, dto);
        return mapToDto(repository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private StudentResponseDto mapToDto(Student student) {
        return new StudentResponseDto(student.getId(), student.getFirstName(), student.getLastName(), 
                                      student.getEmail(), student.getEnrollmentYear(), student.getStatus());
    }

    private void updateEntity(Student student, StudentRequestDto dto) {
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setEnrollmentYear(dto.enrollmentYear());
        student.setStatus(dto.status());
    }
}