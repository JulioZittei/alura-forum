package br.com.alura.forum.controller

import br.com.alura.forum.dto.CreateRespostaRequest
import br.com.alura.forum.dto.RespostaResponse
import br.com.alura.forum.service.RespostaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/respostas")
class RespostaController(
    private val respostaService: RespostaService
) {

    @PostMapping
    fun createResposta(@Valid @RequestBody resposta: CreateRespostaRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<RespostaResponse> {
        val respostaResponse = respostaService.createResposta(resposta)
        val uri = uriBuilder.path("respostas/${respostaResponse.id}").build().toUri()
        return ResponseEntity.created(uri).body(respostaResponse)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteResposta(@PathVariable id: Long) {
        respostaService.deleteResposta(id)
    }
}