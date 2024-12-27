package br.com.alura.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateRespostaRequest(
    @field:NotBlank
    val mensagem: String,
    @field:NotNull
    val idAutor: Long,
    @field:NotNull
    val idTopico: Long,
    val solucao: Boolean
)
