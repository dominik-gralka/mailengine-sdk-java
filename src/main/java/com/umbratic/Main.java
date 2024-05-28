package com.umbratic;
import com.umbratic.mailengine.MailEngine;

public class Main {
    public static void main(String[] args) {
        String bearerToken = "YOUR_BEARER_TOKEN_HERE";

        MailEngine mailEngine = new MailEngine(bearerToken);

        try {
            String response = mailEngine.sendEmail(
                    "john.doe@example.com",
                    "John Doe",
                    "noreply@example.com",
                    "No Reply",
                    "Important Information",
                    "This is a test email content."
            );
            System.out.println("Email sent successfully: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }
    }
}
