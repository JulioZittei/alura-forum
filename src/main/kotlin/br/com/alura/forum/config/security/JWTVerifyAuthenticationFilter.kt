package br.com.alura.forum.config.security

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.util.Constants.Companion.AUTHORIZATION_HEADER
import br.com.alura.forum.util.Constants.Companion.BEARER_TOKEN
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JWTVerifyAuthenticationFilter(
    private val jwtUtil: JWTUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        val token = getToken(bearerToken)
        if (jwtUtil.verify(token)) {
            val authentication = jwtUtil.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(bearerToken: String?): String? {
        return bearerToken?.let {
            if (it.startsWith(BEARER_TOKEN)) {
               return it.replace(BEARER_TOKEN, "")
            }
            return null
        }
    }
}
