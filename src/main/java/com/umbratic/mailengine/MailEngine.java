package com.umbratic.mailengine;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The MailEngine class provides functionality to send emails using an HTTP client.
 */
public class MailEngine {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a MailEngine instance with the given bearer token.
     *
     * @param bearerToken the bearer token used for authorization
     */
    public MailEngine(String bearerToken) {
        this.httpClient = new HttpClient(bearerToken, "https://mailengine.umbratic.com/api");
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends an email with the specified details.
     *
     * @param email the Email object containing the email details
     * @return the response string from the API if the request is successful
     * @throws IOException if an I/O error occurs or if the API response indicates a failure
     */
    public String sendEmail(Email email) throws IOException {
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("recipientEmail", email.getRecipientEmail());
        emailMap.put("recipientName", email.getRecipientName());
        emailMap.put("senderEmail", email.getSenderEmail());
        emailMap.put("senderName", email.getSenderName());
        emailMap.put("emailSubject", email.getEmailSubject());
        emailMap.put("emailContent", email.getEmailContent());

        String jsonPayload = objectMapper.writeValueAsString(emailMap);
        return httpClient.post("/send-email", jsonPayload);
    }
}
