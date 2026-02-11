package com.emailmanager.mail.client;

import com.emailmanager.config.MailServerProperties.ServerConfig;
import com.emailmanager.mail.dto.MailDetailResponse;
import com.emailmanager.mail.dto.MailPageResponse;

import java.util.List;

public interface MailClient {

    boolean supports(String protocol);

    MailPageResponse getMessages(ServerConfig serverConfig, int page, int size);

    MailDetailResponse getMessage(ServerConfig serverConfig, int messageId);

    void deleteMessages(ServerConfig serverConfig, List<Integer> messageIds);
}
