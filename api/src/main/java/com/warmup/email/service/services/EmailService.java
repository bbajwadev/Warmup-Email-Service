package com.warmup.email.service.services;

import com.warmup.email.service.event.SendEmailEvent;

public interface EmailService {
    boolean sendEmail(SendEmailEvent event);

}
