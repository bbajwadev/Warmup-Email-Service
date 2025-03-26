package com.warmup.email.service.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmailAddressResolverTest {

    private EmailAddressResolver resolver = new EmailAddressResolver();

    @Test
    public void testResolveEmail() {
        String tenantId = "tenant1";
        String userId = "user1";
        // Expected: "user1@tenant1.company.com"
        String expected = "user1@tenant1.company.com";
        String actual = resolver.resolve(tenantId, userId);
        assertEquals(expected, actual, "The email should be resolved properly");
    }
}
