package com.emailmanager.mail.dto;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class MailDetailResponse {

    private final int messageNumber;
    private final String subject;
    private final List<String> from;
    private final List<String> to;
    private final Date sentDate;
    private final String body;

    public static MailDetailResponse from(Message message) throws MessagingException {
        return new MailDetailResponse(
                message.getMessageNumber(),
                message.getSubject(),
                message.getFrom() != null
                        ? Arrays.stream(message.getFrom()).map(Object::toString).toList()
                        : List.of(),
                message.getAllRecipients() != null
                        ? Arrays.stream(message.getAllRecipients()).map(Object::toString).toList()
                        : List.of(),
                message.getSentDate(),
                extractBody(message)
        );
    }

    private static String extractBody(Part part) {
        try {
            if (part.isMimeType("text/plain")) {
                return (String) part.getContent();
            }
            if (part.isMimeType("text/html")) {
                return (String) part.getContent();
            }
            if (part.getContent() instanceof Multipart multipart) {
                for (int i = 0; i < multipart.getCount(); i++) {
                    String body = extractBody(multipart.getBodyPart(i));
                    if (body != null) {
                        return body;
                    }
                }
            }
        } catch (MessagingException | IOException e) {
            return null;
        }
        return null;
    }
}
