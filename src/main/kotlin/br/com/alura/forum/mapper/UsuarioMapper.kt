package br.com.alura.forum.mapper

import br.com.alura.forum.dto.CreateUsuarioRequest
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Component

@Component
class UsuarioMapper: Mapper<CreateUsuarioRequest, Usuario> {
    override fun map(u: CreateUsuarioRequest): Usuario {
        return Usuario(u.nome, u.email)
    }
}