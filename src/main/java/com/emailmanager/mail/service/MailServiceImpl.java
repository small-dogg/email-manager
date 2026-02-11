package com.emailmanager.mail.service;

import com.emailmanager.common.exception.BusinessException;
import com.emailmanager.common.exception.ErrorCode;
import com.emailmanager.config.MailServerProperties;
import com.emailmanager.config.MailServerProperties.ServerConfig;
import com.emailmanager.mail.client.MailClient;
import com.emailmanager.mail.dto.MailDetailResponse;
import com.emailmanager.mail.dto.MailPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailServerProperties mailServerProperties;
    private final List<MailClient> mailClients;

    @Override
    public List<ServerConfig> getServers() {
        return mailServerProperties.getServers();
    }

    @Override
    public MailPageResponse getMessages(String serverName, int page, int size, String sort) {
        ServerConfig config = findServerConfig(serverName);
        MailClient client = resolveClient(config.getProtocol());
        return client.getMessages(config, page, size, sort);
    }

    @Override
    public MailDetailResponse getMessage(String serverName, int messageId) {
        ServerConfig config = findServerConfig(serverName);
        MailClient client = resolveClient(config.getProtocol());
        return client.getMessage(config, messageId);
    }

    @Override
    public void deleteMessages(String serverName, List<Integer> messageIds) {
        ServerConfig config = findServerConfig(serverName);
        MailClient client = resolveClient(config.getProtocol());
        client.deleteMessages(config, messageIds);
    }

    private ServerConfig findServerConfig(String serverName) {
        return mailServerProperties.getServers().stream()
                .filter(s -> s.getName().equals(serverName))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.SERVER_NOT_FOUND));
    }

    private MailClient resolveClient(String protocol) {
        return mailClients.stream()
                .filter(c -> c.supports(protocol))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.UNSUPPORTED_PROTOCOL));
    }
}
