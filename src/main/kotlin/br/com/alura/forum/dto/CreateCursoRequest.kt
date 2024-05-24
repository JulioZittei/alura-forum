package br.com.alura.forum.dto

import jakarta.validation.constraints.NotBlank

data class CreateCursoRequest(
    @field:NotBlank
    val nome: String,
    @field:NotBlank
    val categoria: String
)
