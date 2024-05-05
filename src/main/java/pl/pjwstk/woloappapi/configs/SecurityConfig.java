package pl.pjwstk.woloappapi.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/openapi.yaml").permitAll();
                    auth.requestMatchers("/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers("/swagger-resources/**").permitAll();

                    auth.requestMatchers(OPTIONS, "/categories/**").permitAll();
                    auth.requestMatchers(GET, "/categories").permitAll();
                    auth.requestMatchers(GET, "/categories/**").permitAll();
                    auth.requestMatchers(POST,"/categories/**").hasAuthority("CREATE_CATEGORY");
                    auth.requestMatchers(PUT,"/categories/**").hasAuthority("EDIT_CATEGORY");
                    auth.requestMatchers(DELETE,"/categories/**").hasAuthority("DELETE_CATEGORY");

                    auth.requestMatchers(OPTIONS, "/cities/**").permitAll();
                    auth.requestMatchers(GET, "/cities**").permitAll();
                    auth.requestMatchers(GET, "/cities/admin/all").hasAuthority("READ_ALL_CITY");
                    auth.requestMatchers(POST,"/cities/**").hasAuthority("CREATE_CITY");
                    auth.requestMatchers(PUT,"/cities/**").hasAuthority("EDIT_CITY");
                    auth.requestMatchers(DELETE,"/cities/**").hasAuthority("DELETE_CITY");

                    auth.requestMatchers(OPTIONS, "/districts/**").permitAll();
                    auth.requestMatchers(GET, "/districts").permitAll();
                    auth.requestMatchers(GET, "/districts/admin/all").hasAuthority("READ_ALL_DISTRICT");
                    auth.requestMatchers(POST, "/districts/**").hasAuthority("CREATE_DISTRICT");
                    auth.requestMatchers(PUT, "/districts/**").hasAuthority("EDIT_DISTRICT");
                    auth.requestMatchers(DELETE, "/districts/**").hasAuthority("DELETE_DISTRICT");

                    auth.requestMatchers(OPTIONS, "/events/**").permitAll();
                    auth.requestMatchers(GET, "/events").permitAll();
                    auth.requestMatchers(GET, "/events/**").permitAll();
                    auth.requestMatchers( "/events/join").hasAuthority("JOIN_EVENT");
                    auth.requestMatchers( "/events/join/check").hasAuthority("JOIN_EVENT");
                    auth.requestMatchers("/events/refuse").hasAuthority("JOIN_EVENT");
                    auth.requestMatchers(OPTIONS, "/events/add").hasAuthority("CREATE_EVENT");
                    auth.requestMatchers("/events/add").hasAuthority("CREATE_EVENT");
                    auth.requestMatchers(PUT, "/events/**").hasAuthority("EDIT_EVENT");
                    auth.requestMatchers(DELETE, "/events/**").hasAuthority("DELETE_EVENT");
                    auth.requestMatchers("/events/users**").hasAuthority("READ_USERS_BY_SHIFT");
                    auth.requestMatchers("/events/users/pdf").hasAuthority("USERS_LIST_PDF");
                    auth.requestMatchers("events/admin**").permitAll();

                    auth.requestMatchers(OPTIONS, "/reports/**").permitAll();
                    auth.requestMatchers(GET, "/reports/public/**").permitAll();
                    auth.requestMatchers("/reports/event/**").hasAuthority("READ_REPORTS");
                    auth.requestMatchers(POST, "/reports/**").hasAuthority("CREATE_REPORT");
                    auth.requestMatchers(PUT, "/reports/**").hasAuthority("EDIT_REPORT");
                    auth.requestMatchers(DELETE, "/reports/**").hasAuthority("DELETE_REPORT");

                    auth.requestMatchers(GET, "/organisations").permitAll();
                    auth.requestMatchers(GET, "/organisations/**").permitAll();
                    auth.requestMatchers(GET, "/organisations/admin/all").hasAuthority("READ_ALL_ORGANISATION");
                    auth.requestMatchers(OPTIONS, "/organisations/**").permitAll();
                    auth.requestMatchers( "/organisations/add").hasAuthority("CREATE_ORGANISATION");
                    auth.requestMatchers( "/organisations/approve").hasAuthority("APPROVE_ORGANISATION");
                    auth.requestMatchers( "/organisations/disapprove").hasAuthority("APPROVE_ORGANISATION");
                    auth.requestMatchers(PUT, "/organisations/**").hasAuthority("EDIT_ORGANISATION");

                    auth.requestMatchers(OPTIONS, "/roles/**").permitAll();
                    auth.requestMatchers(GET, "/roles").hasAuthority("READ_ROLE");
                    auth.requestMatchers(GET, "/roles/**").permitAll();
                    auth.requestMatchers(POST, "/roles/**").hasAuthority("CREATE_ROLE");
                    auth.requestMatchers(PUT, "/roles/**").hasAuthority("EDIT_ROLE");
                    auth.requestMatchers(DELETE, "/roles/**").hasAuthority("DELETE_ROLE");

                    auth.requestMatchers(OPTIONS,"/users/**").permitAll();
                    auth.requestMatchers(GET, "/users/*/shifts").hasAuthority("READ_USERS_SHIFTS");
                    auth.requestMatchers(GET, "/users/shifts/**").hasAuthority("READ_USERS_EVENTS");
                    auth.requestMatchers(GET, "/users").hasAuthority("READ_USERS");
                    auth.requestMatchers(GET, "/users/**").hasAuthority("READ_USERS");
                    auth.requestMatchers(PUT, "/users/*/edit").hasAuthority("EDIT_USERS");
                    auth.requestMatchers(PUT, "/users/edit").permitAll();
                    auth.requestMatchers(DELETE, "/users/**").hasAuthority("DELETE_USERS");
                    auth.requestMatchers(DELETE, "/users/assign").hasAuthority("ASSIGN_ORGANISATION_TO_USER");
                    auth.requestMatchers(DELETE, "/users/revoke").hasAuthority("ASSIGN_ORGANISATION_TO_USER");
                    auth.requestMatchers(DELETE, "/users/changerole").hasAuthority("CHANGE_USERS_ROLE");
                    auth.anyRequest().authenticated();
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                            .logoutSuccessHandler((request, response, authentication) ->
                                    SecurityContextHolder.clearContext())
                            .permitAll();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
