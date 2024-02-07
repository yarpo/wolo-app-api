package pl.pjwstk.woloappapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Mapowanie do wszystkich zasobów
                .allowedOrigins("http://localhost:3000","http://localhost:5000") // Ustal dozwolone źródła (adresy URL)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Dozwolone metody HTTP
                .allowedHeaders("Content-Type", "Authorization") // Dozwolone nagłówki
                .allowCredentials(false); // Czy zezwalać na przesyłanie ciasteczek
    }
}
