package com.umbratic.mailengine;

import java.io.IOException;

/**
 * Main class to demonstrate the usage of the MailEngine library.
 */
class Main {
    public static void main(String[] args) {

        // Replace the bearer token with your own (without Bearer prefix)
        String bearerToken = "<YOUR_BEARER_TOKEN_HERE>";

        // Create an instance of MailEngine with the bearer token
        MailEngine mailEngine = new MailEngine(bearerToken);

        // Create an Email object with the necessary details
        Email email = new Email(
                "recipient@example.com",
                "Recipient Name",
                "sender@example.com",
                "Sender Name",
                "A letter to Houston",
                "Hello, World!"
        );

        // Send the email and handle any exceptions
        try {
            String response = mailEngine.sendEmail(email);
            System.out.println("Email sent successfully: " + response);
        } catch (IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
