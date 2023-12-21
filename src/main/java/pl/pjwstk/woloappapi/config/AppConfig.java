package pl.pjwstk.woloappapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("file:${user.dir}/.env")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public TokenConfig tokenConfig() {
        String tokenSecret = env.getProperty("TOKEN_SECRET");
        long tokenExpirationMsec = Long.parseLong(Objects.requireNonNullElse(env.getProperty("TOKEN_EXPIRATION_MSEC"), "0"));
        return new TokenConfig(tokenSecret, tokenExpirationMsec);
    }

    public static class TokenConfig {
        private final String tokenSecret;
        private final long tokenExpirationMsec;

        public TokenConfig(String tokenSecret, long tokenExpirationMsec) {
            this.tokenSecret = tokenSecret;
            this.tokenExpirationMsec = tokenExpirationMsec;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }
    }
}
