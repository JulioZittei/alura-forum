package br.com.alura.forum.dto

import jakarta.validation.constraints.NotBlank

data class CreateUsuarioRequest(
    @field:NotBlank
    val nome: String,
    @field:NotBlank
    val email: String,
)
