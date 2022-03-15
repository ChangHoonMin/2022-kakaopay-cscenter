package com.kakaopay.cscenter.config;

import com.kakaopay.cscenter.jwt.TokenManager;
import com.kakaopay.cscenter.security.AuthenticationEntryPointImpl;
import com.kakaopay.cscenter.security.TokenAuthenticationFilter;
import com.kakaopay.cscenter.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.cors.allow-origin}")
    public String allowOrigin;

    private final TokenManager tokenManager;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl(handlerExceptionResolver);
    }

    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenManager);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenAuthenticationProvider());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(allowOrigin);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .headers()
                    .frameOptions().disable()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/v1/inquiries/no-replies").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/v1/inquiries/replies").authenticated()
                    .antMatchers(HttpMethod.PATCH, "/api/v1/inquiries/**").authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }
}