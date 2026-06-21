package ua.university.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.model.dto.TeacherRequestDto;
import ua.university.sms.model.dto.TeacherResponseDto;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.TeacherRepository;
import ua.university.sms.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<TeacherResponseDto> getAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    public TeacherResponseDto getById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
    }

    @Transactional
    public TeacherResponseDto create(TeacherRequestDto dto) {
        Teacher teacher = new Teacher();
        updateEntity(teacher, dto);
        return mapToDto(repository.save(teacher));
    }

    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto dto) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + id));
        updateEntity(teacher, dto);
        return mapToDto(repository.save(teacher));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private TeacherResponseDto mapToDto(Teacher teacher) {
        return new TeacherResponseDto(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), 
                                      teacher.getEmail(), teacher.getDateOfBirth(), teacher.getPosition());
    }

    private void updateEntity(Teacher teacher, TeacherRequestDto dto) {
        teacher.setFirstName(dto.firstName());
        teacher.setLastName(dto.lastName());
        teacher.setEmail(dto.email());
        teacher.setDateOfBirth(dto.dateOfBirth());
        teacher.setPosition(dto.position());
    }
}