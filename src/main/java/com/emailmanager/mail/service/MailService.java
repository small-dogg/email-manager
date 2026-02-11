package com.emailmanager.mail.service;

import com.emailmanager.config.MailServerProperties.ServerConfig;
import com.emailmanager.mail.dto.MailDetailResponse;
import com.emailmanager.mail.dto.MailPageResponse;

import java.util.List;

public interface MailService {

    List<ServerConfig> getServers();

    MailPageResponse getMessages(String serverName, int page, int size, String sort);

    MailDetailResponse getMessage(String serverName, int messageId);

    void deleteMessages(String serverName, List<Integer> messageIds);
}
