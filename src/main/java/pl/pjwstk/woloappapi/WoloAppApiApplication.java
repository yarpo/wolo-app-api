package pl.pjwstk.woloappapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Agata Dobrzyniewicz",
                        email = "s24305@pjwstk.edu.pl"
                ),
                description = "The WoloApp API facilitates the management of events and user participation through Create, Read, Update, and Delete operations. It allows users to interact with the following key functionalities such as exploring events through different filters or joining them.",
                title = "WoloApp",
                termsOfService = "https://opensource.guide/legal/?fbclid=IwAR0NUY2SAm3x5HHkhxOmb6piB-WETsmqeifvIB5_pBrHWFhQu31oYBl7WJs"
        ),
        servers = {
                @Server(
                        description = "local",
                        url = "http://localhost:8080"
                )
        }
)
@SpringBootApplication
public class WoloAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoloAppApiApplication.class, args);
    }
}

