package ua.university.sms.model.dto;

import java.time.LocalDate;
import ua.university.sms.model.entity.TeacherPosition;

public record TeacherResponseDto(
    Long id, 
    String firstName, 
    String lastName, 
    String email, 
    LocalDate dateOfBirth, 
    TeacherPosition position
) {}