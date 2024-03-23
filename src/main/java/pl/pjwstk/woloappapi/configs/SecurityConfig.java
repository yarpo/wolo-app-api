package pl.pjwstk.woloappapi.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pjwstk.woloappapi.security.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers(GET, "/categories").permitAll();
                    auth.requestMatchers(GET, "/categories/**").permitAll();
                    auth.requestMatchers(POST,"/categories/**").permitAll();
                    auth.requestMatchers(PUT,"/categories/**").permitAll();
                    auth.requestMatchers(DELETE,"/categories/**").permitAll();
                    auth.requestMatchers(GET, "/districts").permitAll();
                    auth.requestMatchers(POST, "/districts/**").permitAll();
                    auth.requestMatchers(PUT, "/districts/**").permitAll();
                    auth.requestMatchers(DELETE, "/districts/**").permitAll();
                    auth.requestMatchers(GET, "/events").permitAll();
                    auth.requestMatchers(GET, "/events/**").permitAll();
                    auth.requestMatchers( "/events/join").permitAll();
                    auth.requestMatchers("events/refuse").permitAll();
                    auth.requestMatchers("events/add").permitAll();
                    auth.requestMatchers(PUT, "events/**").permitAll();
                    auth.requestMatchers(DELETE).permitAll();
                    auth.requestMatchers(GET, "/organisations").permitAll();
                    auth.requestMatchers(GET, "/organisations/**").permitAll();
                    auth.requestMatchers( "/organisations/add").permitAll();
                    auth.requestMatchers( "/organisations/approve").permitAll();
                    auth.requestMatchers( "/organisations/disapprove").permitAll();
                    auth.requestMatchers(PUT, "/organisations/**").permitAll();
                    auth.requestMatchers(GET, "/roles").permitAll();
                    auth.requestMatchers(GET, "/roles/**").permitAll();
                    auth.requestMatchers(POST, "/roles/**").permitAll();
                    auth.requestMatchers(PUT, "/roles/**").permitAll();
                    auth.requestMatchers(DELETE, "/roles/**").permitAll();
                    auth.requestMatchers(GET, "/users").permitAll();
                    auth.requestMatchers(GET, "/users/**").permitAll();
                    auth.requestMatchers(PUT, "/users/**").permitAll();
                    auth.requestMatchers(DELETE, "/users/**").permitAll();
                    auth.requestMatchers(DELETE, "/users/assign").permitAll();
                    auth.requestMatchers(DELETE, "/users/revoke").permitAll();
                    auth.requestMatchers(DELETE, "/users//changerole").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
