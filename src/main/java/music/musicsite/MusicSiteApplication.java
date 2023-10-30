package music.musicsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ConfigurationPropertiesScan // ConfigurationProperties 프로퍼티스를 인식하게 하는 오너테이션
@SpringBootApplication
public class MusicSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSiteApplication.class, args);
    }

}
