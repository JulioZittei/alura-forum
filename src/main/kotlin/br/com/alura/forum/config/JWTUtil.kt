package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*


@Component
class JWTUtil(
    private val usuarioService: UsuarioService
) {

    @Value("\${jwt.expiration}")
    private var expiration: Long = 60_000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(email: String, authorities: List<out GrantedAuthority>): String? {
        return Jwts.builder()
            .subject(email)
            .claim("role", authorities)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getKey())
            .compact()
    }

    fun verify(token: String?): Boolean {
        return try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(token: String?): Authentication {
        val email =  Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).payload.subject
        val authorities = usuarioService.loadUserByUsername(email)?.authorities
        return UsernamePasswordAuthenticationToken(email, null, authorities)
    }

    private fun getKey() = Keys.hmacShaKeyFor(secret.toByteArray())
}
