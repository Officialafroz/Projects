package com.elevata.gsrtc.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**",
                                "/oauth2/**", "/api/auth/login/**",
                                "/api/depot-admin/register", "/error",
                                "/api/searchBus/results").permitAll()
                        .requestMatchers("/api/auth/me", "/api/auth/logout").hasAnyRole(
                                 "END_USER", "DEPOT_ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/end-user/**").hasAnyRole("END_USER", "DEPOT_ADMIN")
                        .requestMatchers("/api/depot/**").hasAnyRole("DEPOT_ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/central/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request,
                                                   response,
                                                   authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }))
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2SuccessHandler)
                        .authorizationEndpoint(authz ->
                                authz.baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redir ->
                                redir.baseUri("/login/oauth2/code/*"))
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .logout(AbstractHttpConfigurer::disable)        //Disable default logout
                .build();
    }
}
