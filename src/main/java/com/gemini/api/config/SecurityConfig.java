package com.gemini.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CORS: Allow your Frontend (localhost:3000) to talk to this Backend
            .cors(cors -> cors.configurationSource(request -> {
                var config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                    "http://localhost:3000",
                    "https://gemini-orbit-pied.vercel.app"
                ));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            }))
            // 2. Disable CSRF (not needed for API)
            .csrf(csrf -> csrf.disable())
            // 3. Define which endpoints are public vs protected
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/gemini/**").permitAll() // Public (for now)
                .requestMatchers("/api/orbits/**").authenticated() // LOCKED! Needs Clerk Token
                .anyRequest().permitAll()
            )
            // 4. Verify the Token using the Issuer URL from application.properties
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }
}