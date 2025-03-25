package com.warmup.email.service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuotaService {
    private static final Logger logger = LoggerFactory.getLogger(QuotaService.class);
    private final int DAILY_QUOTA = 1; // Example quota per tenant

    // Map to store tenant quotas: tenantId -> (current date, email count)
    private ConcurrentHashMap<String, DailyQuota> quotaMap = new ConcurrentHashMap<>();

    public boolean canSendEmail(String tenantId) {
        DailyQuota dq = quotaMap.computeIfAbsent(tenantId, id -> new DailyQuota(LocalDate.now(), new AtomicInteger(0)));
        // Reset count if date has changed.
        if (!dq.date.equals(LocalDate.now())) {
            logger.info("Resetting quota for tenant {} since date changed from {} to {}", tenantId, dq.date, LocalDate.now());
            dq.date = LocalDate.now();
            dq.count.set(0);
        }
        logger.info("Checking quota for tenant {}: current count = {} (limit = {})", tenantId, dq.count.get(), DAILY_QUOTA);
        return dq.count.get() < DAILY_QUOTA;
    }

    public void incrementQuota(String tenantId) {
        DailyQuota dq = quotaMap.computeIfAbsent(tenantId, id -> new DailyQuota(LocalDate.now(), new AtomicInteger(0)));
        if (!dq.date.equals(LocalDate.now())) {
            logger.info("Resetting quota for tenant {} before incrementing due to date change from {} to {}", tenantId, dq.date, LocalDate.now());
            dq.date = LocalDate.now();
            dq.count.set(0);
        }
        int newCount = dq.count.incrementAndGet();
        logger.info("Incremented quota for tenant {}: new count is {}", tenantId, newCount);
    }

    public long getDelayForTenant(String tenantId) {
        long delay = 60000; // Delay of 60 seconds
        logger.info("Returning delay of {} ms for tenant {}", delay, tenantId);
        return delay;
    }

    private static class DailyQuota {
        LocalDate date;
        AtomicInteger count;

        DailyQuota(LocalDate date, AtomicInteger count) {
            this.date = date;
            this.count = count;
        }
    }
}
