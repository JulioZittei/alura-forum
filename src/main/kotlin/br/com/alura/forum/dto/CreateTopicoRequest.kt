package br.com.alura.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateTopicoRequest (
    @field:NotBlank
    @field:Size(min = 5, max = 100)
    val titulo: String?,
    @field:NotBlank
    val mensagem: String?,
    @field:NotNull
    val idCurso: Long?,
    @field:NotNull
    val idUsuario: Long?
)