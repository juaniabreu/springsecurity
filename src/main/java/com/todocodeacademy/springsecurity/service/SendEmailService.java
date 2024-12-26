package com.todocodeacademy.springsecurity.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    public void sendEmail(String to, String emailBody) throws ResendException {

        Resend resend = new Resend("re_3X99YBj1_Aeq5fLyeBjXiMgLZ4tLB6t8K");
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Vichenzo <onboarding@resend.dev>")
                .to(to)
                .subject("it works!")
                .html(emailBody)
                .build();
        CreateEmailResponse data = resend.emails().send(params);
        System.out.println(data.getId());
    }
}
