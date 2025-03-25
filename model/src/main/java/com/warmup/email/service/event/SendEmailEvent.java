package com.warmup.email.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailEvent {

    public String toAddress;
    public String tenantId;
    public String userId;
    public String subject;
    public String body;
}
