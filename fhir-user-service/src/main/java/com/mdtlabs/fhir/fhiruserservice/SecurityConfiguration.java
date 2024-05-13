package com.mdtlabs.fhir.fhiruserservice;

import com.mdtlabs.fhir.commonservice.common.AuthenticationFilter;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

/**
 * The {@code SecurityConfiguration} class configures security settings for an application, including
 * authentication and authorization mechanisms, and creates a custom OpenAPI specification
 * for a user service API with security schemes and requirements.
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Configures the security filter chain for HTTP requests, allowing certain methods
     * and endpoints to be accessed without authentication while requiring authentication for all
     * other requests.
     *
     * @param http                 {@link HttpSecurity} used to configure security settings for the application.
     * @param authenticationFilter {@link SecurityFilterChain} handles authentication for incoming requests.
     * @return {@link SecurityFilterChain} returns a SecurityFilterChain object.
     * @throws Exception in case of CORS configuration error.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationFilter authenticationFilter) throws Exception {
        http
                .cors().configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedMethods(Arrays.asList(
                            HttpMethod.DELETE.name(),
                            HttpMethod.GET.name(),
                            HttpMethod.POST.name(),
                            HttpMethod.PUT.name(),
                            HttpMethod.PATCH.name()
                    ));
                    cors.applyPermitDefaultValues();
                    cors.addAllowedOrigin(Constants.ASTERISK_SYMBOL); //NOSONAR
                    cors.addAllowedOriginPattern(Constants.ASTERISK_SYMBOL);
                    return cors;
                })
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .requestMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/webjars/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()  //NOSONAR
                .httpBasic();
        return http.build();
    }
}