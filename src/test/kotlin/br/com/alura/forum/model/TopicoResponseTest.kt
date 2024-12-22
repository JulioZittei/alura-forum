package br.com.alura.forum.model

import br.com.alura.forum.dto.TopicoResponse
import java.time.LocalDateTime

object TopicoResponseTest {
    fun build() = TopicoResponse(
        id = 1,
        titulo = "",
        mensagem = "",
        status = StatusTopico.NAO_RESPONDIDO,
        dataCriacao = LocalDateTime.now(),
        dataAlteracao = LocalDateTime.now(),
    )
}