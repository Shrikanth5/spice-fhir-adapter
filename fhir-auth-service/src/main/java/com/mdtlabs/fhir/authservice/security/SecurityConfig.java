package com.mdtlabs.fhir.authservice.security;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * <p>
 * The {@code SecurityConfig} class is responsible for setting up user authentication and authorization.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccess authenticationSuccess() {
        return new AuthenticationSuccess();
    }

    @Bean
    public AuthenticationFailure authenticationFailure() {
        return new AuthenticationFailure();
    }

    @Bean
    public AuthProvider authenticationProvider() {
        return new AuthProvider();
    }

    @Bean
    public LogoutSuccess logoutSuccess() {
        return new LogoutSuccess();
    }

    /**
     * <p>
     * Configures the CORS (Cross-Origin Resource Sharing) for the application.
     * </p>
     *
     * @return {@link CorsConfigurationSource} the CORS configuration source.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.TRUE);
        config.addAllowedOriginPattern(Constants.ASTERISK_SYMBOL);
        config.addAllowedHeader(Constants.ASTERISK_SYMBOL);
        config.addAllowedMethod(HttpMethod.HEAD);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.PATCH);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        source.registerCorsConfiguration(Constants.ASTERISK_SYMBOL + Constants.FORWARD_SLASH + Constants.ASTERISK_SYMBOL, config);
        return source;
    }

    /**
     * <p>
     * Configures the security filter chain for HTTP requests.
     * </p>
     *
     * @param http {@link HttpSecurity} the HTTP security configuration.
     * @return {@link SecurityFilterChain} the security filter chain configuration.
     * @throws Exception if there is an exception during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/webjars/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/session")
                .usernameParameter(FieldConstants.USERNAME_LOGIN)
                .passwordParameter(FieldConstants.PASSWORD)
                .successHandler(authenticationSuccess())
                .failureHandler(authenticationFailure())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(Boolean.TRUE)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler(logoutSuccess())
                .and()
                .csrf().disable();  //NOSONAR
        return http.build();
    }
}
