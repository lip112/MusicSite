package com.exam.mix.config.email;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "mail.smtp.socketfactory")
@Getter
public class socketFactoryProperties {

    private final String soketclass;
    private final boolean fallback;
    private final int port;

    public socketFactoryProperties(String soketclass, boolean fallback, int port) {
        this.soketclass = soketclass;
        this.fallback = fallback;
        this.port = port;
    }
}
