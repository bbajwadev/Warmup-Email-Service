package com.warmup.email.service.queue;

import com.warmup.email.service.event.SendEmailEvent;

public interface MessageListener {
    void onMessage(SendEmailEvent event);

}
