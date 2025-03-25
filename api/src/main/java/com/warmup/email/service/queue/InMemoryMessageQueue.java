package com.warmup.email.service.queue;

import com.warmup.email.service.event.SendEmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class InMemoryMessageQueue implements MessageQueue {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryMessageQueue.class);
    private BlockingQueue<SendEmailEvent> queue = new LinkedBlockingQueue<>();
    private MessageListener listener;

    @Override
    public void publish(SendEmailEvent event, long delayMillis) {
        try {
            // Simulate delay (in a real system you might use a scheduled executor)
            Thread.sleep(delayMillis);
            queue.put(event);
            logger.info("Event published to queue: {}", event);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void subscribe(MessageListener listener) {
        this.listener = listener;
        // Start a dedicated thread to process queue events
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    SendEmailEvent event = queue.take();
                    listener.onMessage(event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        worker.setDaemon(true);
        worker.start();
    }
}
