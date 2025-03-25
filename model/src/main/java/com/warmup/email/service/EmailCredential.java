package com.warmup.email.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailCredential {
    @Id
    public String id;
    public String tenantId;
    public String userId;
    public String provider;
    public String token;
}
