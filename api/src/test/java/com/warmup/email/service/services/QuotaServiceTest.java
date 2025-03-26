package com.warmup.email.service.services;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuotaServiceTest {

    private QuotaService quotaService;

    @BeforeEach
    public void setUp() {
        quotaService = new QuotaService();
    }

    @Test
    public void testCanSendEmailWhenBelowQuota() {
        // Initially, no emails have been sent for tenant1
        assertTrue(quotaService.canSendEmail("tenant1"), "Should be allowed when quota is not exceeded");
    }

    @Test
    public void testIncrementQuotaAndExceed() {
        String tenantId = "tenant1";
        // Allowed initially
        assertTrue(quotaService.canSendEmail(tenantId));
        // Increment quota (now count = 1, equal to DAILY_QUOTA of 1)
        quotaService.incrementQuota(tenantId);
        // Should not allow more email since quota is reached
        assertFalse(quotaService.canSendEmail(tenantId), "Should not allow sending email after reaching quota");
    }
}