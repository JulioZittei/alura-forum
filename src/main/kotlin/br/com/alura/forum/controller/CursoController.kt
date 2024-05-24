package br.com.alura.forum.controller

import br.com.alura.forum.dto.CreateCursoRequest
import br.com.alura.forum.dto.CursoResponse
import br.com.alura.forum.service.CursoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/cursos")
class CursoController (
    private val cursoService: CursoService
) {

    @GetMapping
    fun getCursos(): List<CursoResponse> {
        return cursoService.findAllCursos()
    }

    @GetMapping("/{id}")
    fun getCursoById(@PathVariable id: Long): CursoResponse {
        return cursoService.findCursoById(id)
    }

    @PostMapping
    fun createCurso(@Valid @RequestBody curso: CreateCursoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<CursoResponse> {
        val createdCurso = cursoService.createCurso(curso)
        val uri = uriBuilder.path("/cursos/${createdCurso.id}").build().toUri()
        return ResponseEntity.created(uri).body(createdCurso)
    }
}