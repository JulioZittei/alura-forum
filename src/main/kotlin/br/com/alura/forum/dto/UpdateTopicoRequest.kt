package br.com.alura.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateTopicoRequest(
    @field:NotBlank
    @field:Size(min = 5, max = 100)
    val titulo: String?,
    @field:NotBlank
    val mensagem: String?,
)
