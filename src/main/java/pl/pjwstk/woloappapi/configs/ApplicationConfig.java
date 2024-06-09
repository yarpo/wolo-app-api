package pl.pjwstk.woloappapi.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pjwstk.woloappapi.service.security.CustomUserDetailsService;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public TokenConfig tokenConfig() {
        var tokenSecret = System.getenv("TOKEN_SECRET");
        var accessTokenExpiration = Long.parseLong(System.getenv("ACCESS_TOKEN_EXPIRATION"));
        var refreshTokenExpiration = Long.parseLong(System.getenv("REFRESH_TOKEN_EXPIRATION"));
        return new TokenConfig(tokenSecret, accessTokenExpiration, refreshTokenExpiration);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
