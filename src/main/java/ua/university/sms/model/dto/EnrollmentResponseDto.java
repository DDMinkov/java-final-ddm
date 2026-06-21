package ua.university.sms.model.dto;

import ua.university.sms.model.entity.Grade;

public record EnrollmentResponseDto(
    Long id, 
    Long studentId, 
    Long courseId, 
    String semester, 
    Integer year, 
    Boolean paid, 
    Grade grade
) {}