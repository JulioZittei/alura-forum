package br.com.alura.forum.controller

import br.com.alura.forum.dto.CreateTopicoRequest
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.dto.UpdateTopicoRequest
import br.com.alura.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController (
        private val topicoService: TopicoService
) {

    @GetMapping
    fun getTopicos(): List<TopicoResponse> {
        return topicoService.findAllTopicos()
    }

    @GetMapping("/{id}")
    fun getTopicoById(@PathVariable id: Long): TopicoResponse{
        return topicoService.findTopicoById(id)
    }

    @PostMapping
    fun createTopico(@Valid @RequestBody topico: CreateTopicoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoResponse> {
        val topicoResponse = topicoService.createTopico(topico)
        val uri = uriBuilder.path("topicos/${topicoResponse.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoResponse)
    }

    @PutMapping("/{id}")
    fun updateTopico(@PathVariable id: Long, @Valid @RequestBody topico: UpdateTopicoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoResponse> {
        val topicoResponse = topicoService.updateTopico(id, topico)
        val uri = uriBuilder.path("topicos/${topicoResponse.id}").build().toUri()
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(topicoResponse)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTopico(@PathVariable id: Long) {
        topicoService.deleteTopico(id)
    }
}