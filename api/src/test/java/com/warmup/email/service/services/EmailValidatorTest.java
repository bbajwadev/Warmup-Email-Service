package com.warmup.email.service.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    private EmailValidator validator = new EmailValidator();

    @Test
    public void testValidEmail() {
        String email = "user1@tenant1.company.com";
        assertTrue(validator.isValid(email), "Valid email should pass the validation");
    }

    @Test
    public void testInvalidEmailFormat() {
        String email = "user1@tenant1";
        assertFalse(validator.isValid(email), "Email missing TLD should fail validation");
    }

    @Test
    public void testThrowawayEmail() {
        String email = "user@tempmail.com";
        assertFalse(validator.isValid(email), "Throwaway email should be rejected");
    }
}
