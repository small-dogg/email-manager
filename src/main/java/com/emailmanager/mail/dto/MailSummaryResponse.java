package com.emailmanager.mail.dto;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class MailSummaryResponse {

    private final int messageNumber;
    private final String subject;
    private final List<String> from;
    private final Date sentDate;

    public static MailSummaryResponse from(Message message) throws MessagingException {
        return new MailSummaryResponse(
                message.getMessageNumber(),
                message.getSubject(),
                message.getFrom() != null
                        ? Arrays.stream(message.getFrom()).map(Object::toString).toList()
                        : List.of(),
                message.getSentDate()
        );
    }
}
