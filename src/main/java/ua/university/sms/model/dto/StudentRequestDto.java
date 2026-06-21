package ua.university.sms.model.dto;

import jakarta.validation.constraints.*;
import ua.university.sms.model.entity.StudentStatus;

public record StudentRequestDto(
    @NotBlank(message = "First name is required") String firstName,
    @NotBlank(message = "Last name is required") String lastName,
    @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
    @NotNull(message = "Enrollment year is required") @Min(2000) Integer enrollmentYear,
    @NotNull(message = "Status is required") StudentStatus status
) {}