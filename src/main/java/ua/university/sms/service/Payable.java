package ua.university.sms.service;

public interface Payable {
    void markAsPaid(Long enrollmentId);
}