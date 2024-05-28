package com.umbratic.mailengine;

/**
 * Represents the details of an email to be sent.
 */
public class Email {
    private final String recipientEmail;
    private final String recipientName;
    private final String senderEmail;
    private final String senderName;
    private final String emailSubject;
    private final String emailContent;

    /**
     * Constructs an Email object with the given details.
     *
     * @param recipientEmail the recipient's email address
     * @param recipientName  the recipient's name
     * @param senderEmail    the sender's email address
     * @param senderName     the sender's name
     * @param emailSubject   the subject of the email
     * @param emailContent   the content of the email
     */
    public Email(String recipientEmail, String recipientName, String senderEmail, String senderName, String emailSubject, String emailContent) {
        this.recipientEmail = recipientEmail;
        this.recipientName = recipientName;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }
}
