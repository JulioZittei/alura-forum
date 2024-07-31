package br.com.alura.forum.config.security

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.dto.CredentialsRequest
import br.com.alura.forum.util.Constants.Companion.AUTHORIZATION_HEADER
import br.com.alura.forum.util.Constants.Companion.BEARER_TOKEN
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val jwtUtil: JWTUtil) :
    UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val (username, password) = ObjectMapper().readValue(request?.inputStream, CredentialsRequest::class.java)
        val token = UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val email = (authResult?.principal as UserDetails).username
        val authorities = (authResult?.principal as UserDetails).authorities
        val token = jwtUtil.generateToken(email,  authorities.toList())
        response?.addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN.plus(token))

        // Cria um objeto de resposta com o token
        val responseBody = mapOf("token" to token)

        // Converte o objeto de resposta para JSON
        val jsonResponse = ObjectMapper().writeValueAsString(responseBody)

        // Define o tipo de conteúdo e escreve a resposta
        response?.contentType = "application/json"
        response?.characterEncoding = "UTF-8"
        response?.writer?.write(jsonResponse)
    }
}
