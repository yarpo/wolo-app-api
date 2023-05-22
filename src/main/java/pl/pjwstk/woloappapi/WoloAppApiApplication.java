package pl.pjwstk.woloappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.pjwstk.woloappapi.controller.DatabaseInitializer;

@SpringBootApplication
public class WoloAppApiApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(WoloAppApiApplication.class, args);
	}
}
