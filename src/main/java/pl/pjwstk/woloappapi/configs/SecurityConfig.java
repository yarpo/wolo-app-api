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
                    auth.requestMatchers(POST,"/categories/**").hasAuthority("CREATE_CATEGORY");
                    auth.requestMatchers(PUT,"/categories/**").hasAuthority("EDIT_CATEGORY");
                    auth.requestMatchers(DELETE,"/categories/**").hasAuthority("DELETE_CATEGORY");
                    auth.requestMatchers(GET, "/districts").permitAll();
                    auth.requestMatchers(POST, "/districts/**").hasAuthority("CREATE_DISTRICT");
                    auth.requestMatchers(PUT, "/districts/**").hasAuthority("EDIT_DISTRICT");
                    auth.requestMatchers(DELETE, "/districts/**").hasAuthority("DELETE_DISTRICT");
                    auth.requestMatchers(GET, "/events").permitAll();
                    auth.requestMatchers(GET, "/events/**").permitAll();
                    auth.requestMatchers( "/events/join").hasAuthority("JOIN_EVENT");
                    auth.requestMatchers("events/refuse").hasAuthority("JOIN_EVENT");
                    auth.requestMatchers("events/add").hasAuthority("CREATE_EVENT");
                    auth.requestMatchers(PUT, "events/**").hasAuthority("EDIT_EVENT");
                    auth.requestMatchers(DELETE).hasAuthority("DELETE_EVENT");
                    auth.requestMatchers(GET, "/organisations").permitAll();
                    auth.requestMatchers(GET, "/organisations/**").permitAll();
                    auth.requestMatchers( "/organisations/add").hasAuthority("CREATE_ORGANISATION");
                    auth.requestMatchers( "/organisations/approve").hasAuthority("APPROVE_ORGANISATION");
                    auth.requestMatchers( "/organisations/disapprove").hasAuthority("APPROVE_ORGANISATION");
                    auth.requestMatchers(PUT, "/organisations/**").hasAuthority("EDIT_ORGANISATION");
                    auth.requestMatchers(GET, "/roles").hasAuthority("READ_ROLE");
                    auth.requestMatchers(GET, "/roles/**").permitAll();
                    auth.requestMatchers(POST, "/roles/**").hasAuthority("CREATE_ROLE");
                    auth.requestMatchers(PUT, "/roles/**").hasAuthority("EDIT_ROLE");
                    auth.requestMatchers(DELETE, "/roles/**").hasAuthority("DELETE_ROLE");
                    auth.requestMatchers(GET, "/users").hasAuthority("READ_USERS");
                    auth.requestMatchers(GET, "/users/**").permitAll();
                    auth.requestMatchers(PUT, "/users/**").hasAuthority("EDIT_USER");
                    auth.requestMatchers(DELETE, "/users/**").hasAuthority("DELETE_USER");
                    auth.requestMatchers(DELETE, "/users/assign").hasAuthority("ASSIGN_ORGANISATION_TO_USER");
                    auth.requestMatchers(DELETE, "/users/revoke").hasAuthority("ASSIGN_ORGANISATION_TO_USER");
                    auth.requestMatchers(DELETE, "/users//changerole").hasAuthority("CHANGE_USERS_ROLE");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
