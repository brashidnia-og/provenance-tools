package com.brashidnia.provenance.tools.service.frameworks.web.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(
    objectMapper: ObjectMapper
) {
//    @Bean
//    fun passwordEncoder(): PasswordEncoder? {
//        return BCryptPasswordEncoder()
//    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService? {
        val admin = User
            .withUsername("admin")
            .password("{noop}SETPASSWORDHERE")
            .roles("USER", "ADMIN")
            .build()
        val figureUser = User
            .withUsername("figure")
            .password("figure")
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(admin, figureUser)
    }

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/").permitAll()
            .anyExchange().authenticated()
            .and()
            .httpBasic()
            .and()
            .logout()
            .logoutUrl("/api/logout") // POST request
            .and()
            .formLogin().disable()
            .build();
    }
}