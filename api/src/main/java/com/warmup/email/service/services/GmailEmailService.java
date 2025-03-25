package com.warmup.email.service.services;

import com.warmup.email.service.event.SendEmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("gmailService")
public class GmailEmailService implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(GmailEmailService.class);

    @Override
    public boolean sendEmail(SendEmailEvent event) {
        logger.info("Sending email via Gmail: {}", event);
        // Simulate sending email (integrate with Gmail API in real implementation)
        return true;
    }
}
