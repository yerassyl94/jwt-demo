package com.yerassyl.jwt.security

import com.yerassyl.jwt.config.JwtAuthorizationFilter
import com.yerassyl.jwt.config.SecurityConstants
import com.yerassyl.jwt.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(val securityConstants: SecurityConstants, val userService: UserService) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/public/**","/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JwtAuthorizationFilter(authenticationManager(), securityConstants))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}