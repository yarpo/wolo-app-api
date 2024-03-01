package pl.pjwstk.woloappapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Bean
    public WebClient localClient() {
        return WebClient.create("http://127.0.0.1:5001");
    }
}
