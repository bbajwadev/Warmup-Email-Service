package com.warmup.email.service.queue;

import com.warmup.email.service.event.SendEmailEvent;

public interface MessageQueue {
    void publish(SendEmailEvent event, long delayMillis);
    void subscribe(MessageListener listener);
}
