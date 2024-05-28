package com.umbratic.mailengine;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MailEngine {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public MailEngine(String bearerToken) {
        this.httpClient = new HttpClient(bearerToken, "https://mailengine.umbratic.com/api");
        this.objectMapper = new ObjectMapper();
    }

    public String sendEmail(
            String recipientEmail,
            String recipientName,
            String senderEmail,
            String senderName,
            String emailSubject,
            String emailContent
    ) throws IOException {
        Map<String, Object> email = new HashMap<>();
        email.put("recipientEmail", recipientEmail);
        email.put("recipientName", recipientName);
        email.put("senderEmail", senderEmail);
        email.put("senderName", senderName);
        email.put("emailSubject", emailSubject);
        email.put("emailContent", emailContent);

        String jsonPayload = objectMapper.writeValueAsString(email);
        return httpClient.post("/send-email", jsonPayload);
    }
}
