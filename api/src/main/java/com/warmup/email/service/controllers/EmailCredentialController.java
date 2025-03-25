package com.warmup.email.service.controllers;

import com.warmup.email.service.EmailCredential;
import com.warmup.email.service.repository.EmailCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credentials")
public class EmailCredentialController {
    @Autowired
    private EmailCredentialRepository repository;

    @PostMapping
    public EmailCredential saveCredential(@RequestBody EmailCredential credential) {
        return repository.save(credential);
    }

    @GetMapping
    public List<EmailCredential> getAllCredentials() {
        return repository.findAll();
    }
}
