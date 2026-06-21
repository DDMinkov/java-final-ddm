package ua.university.sms.model.dto;

import jakarta.validation.constraints.*;

public record EnrollmentRequestDto(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Course ID is required") Long courseId,
    @NotBlank(message = "Semester is required") String semester,
    @NotNull(message = "Year is required") Integer year
) {}