package pl.pjwstk.woloappapi.config;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pjwstk.woloappapi.repository.UserRepository;
import pl.pjwstk.woloappapi.security.CustomUserDetailsService;
import pl.pjwstk.woloappapi.security.RestAuthenticationEntryPoint;
import pl.pjwstk.woloappapi.security.TokenAuthenticationFilter;
import pl.pjwstk.woloappapi.security.UserRole;
import pl.pjwstk.woloappapi.security.oauth2.CustomOAuth2UserService;
import pl.pjwstk.woloappapi.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import pl.pjwstk.woloappapi.security.oauth2.OAuth2AuthenticationFailureHandler;
import pl.pjwstk.woloappapi.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/auth/login").permitAll();
                    auth.requestMatchers("/auth/signup").permitAll();
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers("/health").hasAuthority(UserRole.USER.name());
                    auth.anyRequest().permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(new RestAuthenticationEntryPoint());
                })
                .oauth2Login(oauth2 ->{
                        oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
                        oauth2.successHandler(oAuth2AuthenticationSuccessHandler);
                        oauth2.failureHandler(oAuth2AuthenticationFailureHandler);
                        oauth2.defaultSuccessUrl("http://localhost:3000/");
                })
                .sessionManagement(s -> {
                    s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
