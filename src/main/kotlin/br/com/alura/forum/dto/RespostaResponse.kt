package br.com.alura.forum.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RespostaResponse(
    val mensagem: String,
    val idAutor: Long,
    val idTopico: Long,
    val solucao: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val id: Long
)