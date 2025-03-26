package com.warmup.email.service.services;

import com.warmup.email.service.event.SendEmailEvent;
import com.warmup.email.service.queue.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class EmailProcessorTest {

    @Mock
    private MessageQueue messageQueue;

    @Mock
    private QuotaService quotaService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private EmailAddressResolver emailAddressResolver;

    @Mock
    private EmailServiceFactory emailServiceFactory;

    @InjectMocks
    private EmailProcessor emailProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessValidEmailEvent() {
        // Create a sample event
        SendEmailEvent event = new SendEmailEvent();
        event.setTenantId("tenant1");
        event.setUserId("user1");
        event.setSubject("Test Email");
        event.setBody("Hello, this is a test.");

        // Configure mocks:
        // Simulate the EmailAddressResolver combining tenantId and userId properly.
        when(emailAddressResolver.resolve("tenant1", "user1")).thenReturn("user1@tenant1.company.com");
        // Allow sending (quota is available)
        when(quotaService.canSendEmail("tenant1")).thenReturn(true);
        // Email is valid
        when(emailValidator.isValid("user1@tenant1.company.com")).thenReturn(true);
        // Simulate a dummy email service that sends successfully
        EmailService dummyEmailService = event1 -> true;
        when(emailServiceFactory.getEmailService("tenant1", "user1")).thenReturn(dummyEmailService);

        // Act: Process the event
        emailProcessor.onMessage(event);

        // Verify: The quota should be incremented since the email is sent successfully
        verify(quotaService, times(1)).incrementQuota("tenant1");
        // Ensure that the event is not re-queued (since sending was successful)
        verify(messageQueue, never()).publish(any(SendEmailEvent.class), anyLong());
    }
}