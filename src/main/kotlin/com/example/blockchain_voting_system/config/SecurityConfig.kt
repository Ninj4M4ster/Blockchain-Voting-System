package com.example.blockchain_voting_system.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig (private val jwtAuthConverter: JwtAuthConverter) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated()
            }
        httpSecurity.oauth2ResourceServer { oauth2 ->
            oauth2.jwt { jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)
            }
        }
        return httpSecurity.build()
    }

}