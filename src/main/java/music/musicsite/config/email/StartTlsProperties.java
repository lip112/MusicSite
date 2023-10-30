package music.musicsite.config.email;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail.smtp.starttls")
@Getter
public class StartTlsProperties {

    private final boolean starttls;
    private final boolean startlls_required;

    public StartTlsProperties(boolean required, boolean enable) {
        this.starttls = required;
        this.startlls_required = enable;
    }
}
