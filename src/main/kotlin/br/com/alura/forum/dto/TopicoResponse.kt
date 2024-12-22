package br.com.alura.forum.dto

import br.com.alura.forum.model.StatusTopico
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TopicoResponse (
    val id: Long,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val dataCriacao: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val dataAlteracao: LocalDateTime?
)
