package com.emailmanager.mail.client;

import com.emailmanager.common.exception.BusinessException;
import com.emailmanager.common.exception.ErrorCode;
import com.emailmanager.config.MailServerProperties.ServerConfig;
import com.emailmanager.mail.dto.MailDetailResponse;
import com.emailmanager.mail.dto.MailPageResponse;
import com.emailmanager.mail.dto.MailSummaryResponse;
import jakarta.mail.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class Pop3MailClient implements MailClient {

    @Override
    public boolean supports(String protocol) {
        return "pop3".equalsIgnoreCase(protocol);
    }

    @Override
    public MailPageResponse getMessages(ServerConfig serverConfig, int page, int size, String sort) {
        try (Store store = connectStore(serverConfig);
             Folder folder = openFolder(store, Folder.READ_ONLY)) {

            int totalMessages = folder.getMessageCount();
            int totalPages = (int) Math.ceil((double) totalMessages / size);
            boolean asc = "asc".equalsIgnoreCase(sort);

            int start;
            int end;
            if (asc) {
                start = page * size + 1;
                end = Math.min(start + size - 1, totalMessages);
            } else {
                end = totalMessages - (page * size);
                start = Math.max(end - size + 1, 1);
            }

            List<MailSummaryResponse> summaries = new ArrayList<>();
            if (start <= totalMessages && end >= 1) {
                Message[] messages = folder.getMessages(start, end);
                if (asc) {
                    for (Message message : messages) {
                        summaries.add(MailSummaryResponse.from(message));
                    }
                } else {
                    for (int i = messages.length - 1; i >= 0; i--) {
                        summaries.add(MailSummaryResponse.from(messages[i]));
                    }
                }
            }

            return new MailPageResponse(page, size, totalMessages, totalPages, summaries);
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.MAIL_CONNECTION_FAILED);
        }
    }

    @Override
    public MailDetailResponse getMessage(ServerConfig serverConfig, int messageId) {
        try (Store store = connectStore(serverConfig);
             Folder folder = openFolder(store, Folder.READ_ONLY)) {

            Message message = folder.getMessage(messageId);
            if (message == null) {
                throw new BusinessException(ErrorCode.MESSAGE_NOT_FOUND);
            }
            return MailDetailResponse.from(message);
        } catch (BusinessException e) {
            throw e;
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.MAIL_OPERATION_FAILED);
        }
    }

    @Override
    public void deleteMessages(ServerConfig serverConfig, List<Integer> messageIds) {
        try (Store store = connectStore(serverConfig);
             Folder folder = openFolder(store, Folder.READ_WRITE)) {

            for (int messageId : messageIds) {
                Message message = folder.getMessage(messageId);
                if (message == null) {
                    throw new BusinessException(ErrorCode.MESSAGE_NOT_FOUND);
                }
                message.setFlag(Flags.Flag.DELETED, true);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.MAIL_OPERATION_FAILED);
        }
    }

    private Store connectStore(ServerConfig config) throws MessagingException {
        Properties props = new Properties();
        String protocol = config.isSsl() ? "pop3s" : "pop3";
        props.put("mail.store.protocol", protocol);
        props.put("mail." + protocol + ".host", config.getHost());
        props.put("mail." + protocol + ".port", String.valueOf(config.getPort()));

        Session session = Session.getInstance(props);
        Store store = session.getStore(protocol);
        store.connect(config.getUsername(), config.getPassword());
        return store;
    }

    private Folder openFolder(Store store, int mode) throws MessagingException {
        Folder folder = store.getFolder("INBOX");
        folder.open(mode);
        return folder;
    }
}
