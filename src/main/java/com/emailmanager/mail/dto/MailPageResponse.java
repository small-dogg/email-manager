package com.emailmanager.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MailPageResponse {

    private final int page;
    private final int size;
    private final int totalMessages;
    private final int totalPages;
    private final List<MailSummaryResponse> messages;
}
