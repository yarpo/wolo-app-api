package pl.pjwstk.woloappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.pjwstk.woloappapi.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WoloAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoloAppApiApplication.class, args);
    }
}
//
