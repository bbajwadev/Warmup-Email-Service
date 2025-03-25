package com.warmup.email.service.controllers;

import com.warmup.email.service.event.SendEmailEvent;
import com.warmup.email.service.queue.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestEmailController {

    @Autowired
    private MessageQueue messageQueue;

    @PostMapping("/send-email")
    public String sendTestEmail(@RequestBody SendEmailEvent event) {
        // Publish the event with no delay for testing.
        messageQueue.publish(event, 0);
        return "Event published: " + event;
    }
}