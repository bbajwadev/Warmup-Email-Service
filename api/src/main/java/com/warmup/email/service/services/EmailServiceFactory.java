package com.warmup.email.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceFactory {
    @Autowired
    private GmailEmailService gmailEmailService;

    @Autowired
    private OutlookEmailService outlookEmailService;

    // For demonstration, we simply choose based on userId content.
    // In a real scenario, you’d lookup stored credentials.
    public EmailService getEmailService(String tenantId, String userId) {
        if(userId.toLowerCase().contains("gmail")) {
            return gmailEmailService;
        } else {
            return outlookEmailService;
        }
    }
}
