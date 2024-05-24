package br.com.alura.forum.service

import br.com.alura.forum.dto.CreateUsuarioRequest
import br.com.alura.forum.dto.UsuarioResponse
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.UsuarioMapper
import br.com.alura.forum.mapper.UsuarioResponseMapper
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService (
    private val usuarioRepository: UsuarioRepository,
    private val usuarioResponseMapper: UsuarioResponseMapper,
    private val usuarioMapper: UsuarioMapper
) {

    companion object {
        const val USUARIO_NAO_ENCONTRADO: String = "Usuário não encontrado"
    }
    fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun findById(id: Long): Usuario {
        return usuarioRepository.findById(id).orElseThrow {NotFoundException(USUARIO_NAO_ENCONTRADO)}
    }

    fun findAllUsuarios(): List<UsuarioResponse> {
        return usuarioRepository.findAll().map {u -> usuarioResponseMapper.map(u)}
    }

    fun findUsuarioById(id: Long): UsuarioResponse {
        val existingUsuario = usuarioRepository.findById(id).orElseThrow {NotFoundException(USUARIO_NAO_ENCONTRADO)}
        return usuarioResponseMapper.map(existingUsuario)
    }

    fun createUsuario(usuario: CreateUsuarioRequest): UsuarioResponse {
        val createdUsuario = usuarioRepository.save(usuarioMapper.map(usuario))
        return usuarioResponseMapper.map(createdUsuario)
    }


}
