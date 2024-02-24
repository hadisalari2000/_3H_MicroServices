package org._3HCompany.microservices.uaa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println(http);
        return http

                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeExchange) -> authorizeExchange
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/global/**",
                                "/api/slider/**")
                        .permitAll()
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)

                /*.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/apis/auth/**").permitAll()
                        .anyRequest().authenticated())

                .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
                .httpBasic(Customizer.withDefaults())*/

                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

        /*http
                .csrf(Customizer.withDefaults()).disable()
                .authorizeHttpRequests()
                .requestMatchers("/apis/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);*/

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**");
    }

}
