package music.musicsite.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@RequiredArgsConstructor
@Configuration
public class EmailConfig {
    private final EmailProperties emailProperties;
    private final socketFactoryProperties socketFactoryProperties;
    private final StartTlsProperties startTlsProperties;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername(emailProperties.getId());
        javaMailSender.setPassword(emailProperties.getPassword());
        javaMailSender.setPort(emailProperties.getPort());
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties pt = new Properties();
        pt.put("mail.smtp.auth", emailProperties.isAuth());
        pt.put("mail.smtp.starttls.enable", startTlsProperties.isStarttls());
        pt.put("mail.smtp.starttls.required", startTlsProperties.isStartlls_required());
        pt.put("mail.smtp.socketFactory.fallback", socketFactoryProperties.isFallback());
        pt.put("mail.smtp.socketFactory.port", socketFactoryProperties.getPort());
        pt.put("mail.smtp.socketFactory.class", socketFactoryProperties.getSoketclass());
        return pt;
    }
}
