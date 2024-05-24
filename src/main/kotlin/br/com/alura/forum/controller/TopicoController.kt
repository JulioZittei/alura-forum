package br.com.alura.forum.controller

import br.com.alura.forum.dto.CreateTopicoRequest
import br.com.alura.forum.dto.TopicoReport
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.dto.UpdateTopicoRequest
import br.com.alura.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    @Cacheable("topicoslist")
    fun getTopicos(@RequestParam(required = false) curso: String?, @PageableDefault(size = 5, page = 1) pagination: Pageable): Page<TopicoResponse> {
        val pageNumber = if (pagination.pageNumber >= 1) pagination.pageNumber - 1 else 0
        return topicoService.findAllTopicos(curso, pagination.withPage(pageNumber))
    }

    @GetMapping("/{id}")
    fun getTopicoById(@PathVariable id: Long): TopicoResponse{
        return topicoService.findTopicoById(id)
    }

    @PostMapping
    @CacheEvict("topicoslist", allEntries = true)
    fun createTopico(@Valid @RequestBody topico: CreateTopicoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoResponse> {
        val topicoResponse = topicoService.createTopico(topico)
        val uri = uriBuilder.path("topicos/${topicoResponse.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoResponse)
    }

    @PutMapping("/{id}")
    @CacheEvict("topicoslist", allEntries = true)
    fun updateTopico(@PathVariable id: Long, @Valid @RequestBody topico: UpdateTopicoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoResponse> {
        val topicoResponse = topicoService.updateTopico(id, topico)
        val uri = uriBuilder.path("topicos/${topicoResponse.id}").build().toUri()
        return ResponseEntity.status(HttpStatus.OK).location(uri).body(topicoResponse)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("topicoslist", allEntries = true)
    fun deleteTopico(@PathVariable id: Long) {
        topicoService.deleteTopico(id)
    }

    @GetMapping("/report")
    fun getReport(): List<TopicoReport> {
        return topicoService.report()
    }
}