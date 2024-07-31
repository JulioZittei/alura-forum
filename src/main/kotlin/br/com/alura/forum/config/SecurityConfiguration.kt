package br.com.alura.forum.config

import br.com.alura.forum.config.security.JWTAuthenticationFilter
import br.com.alura.forum.config.security.JWTVerifyAuthenticationFilter
import br.com.alura.forum.util.Constants.Companion.TOPICOS_PATH
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtUtil: JWTUtil,
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationFilter = JWTAuthenticationFilter(getAuthenticationManager(authenticationConfiguration), jwtUtil)
        authenticationFilter.setFilterProcessesUrl("/auth/login")
        return http.csrf { csrf -> csrf.disable() }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests(
                Customizer { auth ->
                    auth
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, TOPICOS_PATH).hasAnyAuthority("LEITURA", "LEITURA_ESCRITA")
                        .requestMatchers(HttpMethod.POST, TOPICOS_PATH).hasAnyAuthority("ESCRITA", "LEITURA_ESCRITA")
                        .requestMatchers(HttpMethod.PUT, TOPICOS_PATH).hasAnyAuthority("ESCRITA", "LEITURA_ESCRITA")
                        .requestMatchers(HttpMethod.DELETE, TOPICOS_PATH).hasAnyAuthority("ESCRITA", "LEITURA_ESCRITA")
                        .anyRequest().authenticated()
                }
            )
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter().javaClass)
            .addFilterBefore(JWTVerifyAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
            .build()
    }

    @Bean
    fun getAuthenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}