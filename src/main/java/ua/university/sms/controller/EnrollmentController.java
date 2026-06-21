package ua.university.sms.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.EnrollmentRequestDto;
import ua.university.sms.model.dto.EnrollmentResponseDto;
import ua.university.sms.model.entity.Grade;
import ua.university.sms.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<EnrollmentResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> create(@Valid @RequestBody EnrollmentRequestDto dto) {
        return new ResponseEntity<>(service.createEnrollment(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/grade")
    public ResponseEntity<EnrollmentResponseDto> updateGrade(@PathVariable Long id, @RequestParam Grade grade) {
        return ResponseEntity.ok(service.updateGrade(id, grade));
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        service.markAsPaid(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}