package br.com.alura.forum.mapper

import br.com.alura.forum.dto.UsuarioResponse
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Component

@Component
class UsuarioResponseMapper():Mapper<Usuario, UsuarioResponse> {
    override fun map(u: Usuario): UsuarioResponse {
        return UsuarioResponse(u.id!!, u.nome, u.email)
    }
}