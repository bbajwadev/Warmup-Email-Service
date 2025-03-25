package com.warmup.email.service.services;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    public boolean isValid(String email) {
        if(email == null || !VALID_EMAIL_REGEX.matcher(email).matches()){
            return false;
        }
        // Reject throwaway domains (example)
        if(email.toLowerCase().endsWith("@tempmail.com")) {
            return false;
        }
        return true;
    }
}
