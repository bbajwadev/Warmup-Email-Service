package com.warmup.email.service.services;

import com.warmup.email.service.event.SendEmailEvent;
import com.warmup.email.service.queue.MessageListener;
import com.warmup.email.service.queue.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailProcessor implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(EmailProcessor.class);

    @Autowired
    private MessageQueue messageQueue;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private EmailAddressResolver emailAddressResolver;

    @Autowired
    private EmailServiceFactory emailServiceFactory;

    // Automatically subscribe to the queue upon initialization.
    @Autowired
    public void init() {
        messageQueue.subscribe(this);
    }

    @Override
    public void onMessage(SendEmailEvent event) {
        logger.info("Received event: {}", event);
        // Resolve the email address for the given tenant and user.
        String resolvedEmail = emailAddressResolver.resolve(event.getTenantId(), event.getUserId());
        event.setToAddress(resolvedEmail);
        logger.info("Resolved email address: {}", resolvedEmail);

        // Check if the tenant's daily quota allows sending.
        if(!quotaService.canSendEmail(event.getTenantId())) {
            logger.warn("Daily quota exceeded for tenant: {}. Re-queuing event.", event.getTenantId());
            messageQueue.publish(event, quotaService.getDelayForTenant(event.getTenantId()));
            return;
        }

        // Validate the resolved email address.
        if(!emailValidator.isValid(resolvedEmail)) {
            logger.error("Email address {} is invalid or marked as throwaway. Discarding event.", resolvedEmail);
            return;
        }

        // Select the correct email service based on tenant/user (or stored credentials).
        EmailService emailService = emailServiceFactory.getEmailService(event.getTenantId(), event.getUserId());
        boolean success = emailService.sendEmail(event);
        if(success) {
            logger.info("Email sent successfully to {}", resolvedEmail);
            quotaService.incrementQuota(event.getTenantId());
        } else {
            logger.error("Failed to send email to {}. Re-queuing event.", resolvedEmail);
            // Re-queue after a delay (e.g., 5 seconds)
            messageQueue.publish(event, 5000);
        }
    }
}
