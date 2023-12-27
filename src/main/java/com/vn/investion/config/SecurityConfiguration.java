package com.vn.investion.config;

import com.vn.investion.repo.UserRepository;
import jakarta.servlet.DispatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final UserRepository userRepository;

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
                .requestMatchers(AntPathRequestMatcher
                                .antMatcher("/"),
                        AntPathRequestMatcher
                                .antMatcher("/error"),
                        AntPathRequestMatcher
                                .antMatcher("/swagger-ui/*"),
                        AntPathRequestMatcher
                                .antMatcher("/v3/api-docs"),
                        AntPathRequestMatcher
                                .antMatcher("/v3/api-docs/*"),
                        AntPathRequestMatcher
                                .antMatcher("/favicon.ico"),
                        AntPathRequestMatcher
                                .antMatcher("/actuator/*"),
                        AntPathRequestMatcher
                                .antMatcher("/api/v1/auth/*"),
                        AntPathRequestMatcher
                                .antMatcher("/api/v1/package/invests"),
                        AntPathRequestMatcher
                                .antMatcher("/api/v1/package/leaders"),
                        AntPathRequestMatcher
                                .antMatcher("/api/v1/image/download/*"),
                        AntPathRequestMatcher
                                .antMatcher("/api/v1/demo")
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        return http.authenticationProvider(authenticationProvider())
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found !"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> authSuccess() {
        return event -> {
            var auth = event.getAuthentication();
            log.info("LOGIN SUCCESSFUL [{}] - {}", auth.getClass().getSimpleName(), auth.getName());
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
