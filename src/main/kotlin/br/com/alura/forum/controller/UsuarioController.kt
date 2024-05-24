package br.com.alura.forum.controller

import br.com.alura.forum.dto.CreateUsuarioRequest
import br.com.alura.forum.dto.UsuarioResponse
import br.com.alura.forum.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @GetMapping
    fun getCursos(): List<UsuarioResponse> {
        return usuarioService.findAllUsuarios()
    }

    @GetMapping("/{id}")
    fun getCursoById(@PathVariable id: Long): UsuarioResponse {
        return usuarioService.findUsuarioById(id)
    }

    @PostMapping
    fun createUsuario(@Valid @RequestBody usuario: CreateUsuarioRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<UsuarioResponse> {
        val createdUsuario = usuarioService.createUsuario(usuario)
        val uri = uriBuilder.path("/usuarios/${createdUsuario.id}").build().toUri()
        return ResponseEntity.created(uri).body(createdUsuario)
    }
}