package ua.university.sms.model.dto;

public record CourseResponseDto(
    Long id, 
    String name, 
    Integer credits, 
    String description, 
    Long teacherId
) {}