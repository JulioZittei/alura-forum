package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class UsuarioService (
    private var usuarios: List<Usuario> = mutableListOf()
) {

    companion object {
        const val USUARIO_NAO_ENCONTRADO: String = "Usuário não encontrado"
    }

    init {
        usuarios = mutableListOf(
            Usuario(1, "Julio Zittei", "julio@mail.com")
        )
    }

    fun findAllUsuarios(): List<Usuario> {
        return usuarios
    }

    fun findUsuarioById(id: Long): Usuario {
        return usuarios.find { u -> u.id?.equals(id) ?: false } ?: throw NotFoundException(USUARIO_NAO_ENCONTRADO)
    }

}
