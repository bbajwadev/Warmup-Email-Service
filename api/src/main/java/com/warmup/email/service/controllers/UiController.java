package com.warmup.email.service.controllers;

import com.warmup.email.service.EmailCredential;
import com.warmup.email.service.event.SendEmailEvent;
import com.warmup.email.service.repository.EmailCredentialRepository;
import com.warmup.email.service.queue.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UiController {

    @Autowired
    private EmailCredentialRepository credentialRepository;

    @Autowired
    private MessageQueue messageQueue;

    // Serve the main UI page
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("credential", new EmailCredential());
        model.addAttribute("sendEmailEvent", new SendEmailEvent());
        model.addAttribute("credentials", credentialRepository.findAll());
        return "index";  // returns src/main/resources/templates/index.html
    }

    // Handle credential linking (POST to /link-credential)
    @PostMapping("/link-credential")
    public String linkCredential(@ModelAttribute EmailCredential credential, Model model) {
        credentialRepository.save(credential);
        model.addAttribute("credentials", credentialRepository.findAll());
        model.addAttribute("sendEmailEvent", new SendEmailEvent());
        model.addAttribute("message", "Credential saved successfully!");
        return "index";
    }

    // Handle sending an email event (POST to /send-email)
    @PostMapping("/send-email")
    public String sendEmail(@ModelAttribute SendEmailEvent event, Model model) {
        // Publish the event immediately (with 0 delay)
        messageQueue.publish(event, 0);
        model.addAttribute("credentials", credentialRepository.findAll());
        model.addAttribute("credential", new EmailCredential());
        model.addAttribute("sendEmailEvent", new SendEmailEvent());
        model.addAttribute("message", "Email event published!");
        return "index";
    }
}
