package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.EnrollmentRequestDto;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Grade;
import ua.university.sms.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollment Management", description = "Управління зарахуваннями, оплатами та оцінками")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @Operation(summary = "Створити нове зарахування")
    public ResponseEntity<Enrollment> enrollStudent(@Valid @RequestBody EnrollmentRequestDto dto) {
        return new ResponseEntity<>(enrollmentService.createEnrollment(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/grade")
    @Operation(summary = "Поставити чи оновити оцінку")
    public ResponseEntity<Void> setGrade(@PathVariable Long id, @RequestParam Grade grade) {
        enrollmentService.updateGrade(id, grade);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/paid")
    @Operation(summary = "Позначити курс як оплачений")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        enrollmentService.markAsPaid(id);
        return ResponseEntity.ok().build();
    }
}