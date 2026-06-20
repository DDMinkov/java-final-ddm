package ua.university.sms.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequestDto(
    @NotNull Long studentId,
    @NotNull Long courseId,
    @NotBlank String semester,
    @NotNull Integer year
) {}