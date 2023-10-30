package music.musicsite.config.email;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail.smtp")
@Getter
public class EmailProperties {
    private final boolean auth;
    private final int port;
    private final String id;
    private final String password;

    public EmailProperties(boolean auth, int port, String id, String password) {
        this.auth = auth;
        this.port = port;
        this.id = id;
        this.password = password;
    }
}
