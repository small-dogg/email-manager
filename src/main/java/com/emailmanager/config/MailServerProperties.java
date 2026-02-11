package com.emailmanager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mail")
public class MailServerProperties {

    private List<ServerConfig> servers = new ArrayList<>();

    @Getter
    @Setter
    public static class ServerConfig {
        private String name;
        private String protocol;
        private String host;
        private int port;
        private String username;
        private String password;
        private boolean ssl;
    }
}
