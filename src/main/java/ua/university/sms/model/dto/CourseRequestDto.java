package ua.university.sms.model.dto;

import jakarta.validation.constraints.*;

public record CourseRequestDto(
    @NotBlank(message = "Course name is required") String name,
    @NotNull(message = "Credits are required") @Min(1) @Max(10) Integer credits,
    String description,
    @NotNull(message = "Teacher ID is required") Long teacherId
) {}