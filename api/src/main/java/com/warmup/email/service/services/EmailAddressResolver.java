package com.warmup.email.service.services;

import org.springframework.stereotype.Component;

@Component
public class EmailAddressResolver {
    // In a real application, this might query a user service or database.
    public String resolve(String tenantId, String userId) {
        // For demonstration, we simply combine the values.
        return userId + "@" + tenantId + ".company.com";
    }
}
