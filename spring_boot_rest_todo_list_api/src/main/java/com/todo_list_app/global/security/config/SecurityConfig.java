package com.todo_list_app.global.security.config;

import com.todo_list_app.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)

        throws Exception {

        httpSecurity

            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/v1/auth/**").permitAll()

                .anyRequest().authenticated()

            )

            .sessionManagement(session ->

                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            )

            .authenticationProvider(authenticationProvider)

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

//    @Bean
//    public FilterRegistrationBean<Filter> corsFilter() {
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.addAllowedOrigin("*");
//
//        config.addAllowedHeader("*");
//
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(
//
//                new CorsFilter(source)
//
//        );
//
//        bean.setOrder(0);
//
//        return bean;
//
//    }

}
