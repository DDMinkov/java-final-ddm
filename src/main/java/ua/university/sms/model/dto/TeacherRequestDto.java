package ua.university.sms.model.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import ua.university.sms.model.entity.TeacherPosition;

public record TeacherRequestDto(
    @NotBlank(message = "First name is required") String firstName,
    @NotBlank(message = "Last name is required") String lastName,
    @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
    @NotNull(message = "Date of birth is required") LocalDate dateOfBirth,
    @NotNull(message = "Position is required") TeacherPosition position
) {}