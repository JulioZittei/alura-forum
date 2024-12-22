package br.com.alura.forum.model

import java.time.LocalDateTime

object TopicoTest {

    fun build() = Topico(
        id = 1,
        titulo = "Kotlin Basico",
        mensagem = "Aprendendo Kotlin básico",
        curso = CursoTest.build(),
        autor = UsuarioTest.build(),
        dataCriacao = LocalDateTime.now(),
        dataAlteracao = LocalDateTime.now(),
        status = StatusTopico.NAO_RESPONDIDO,
        respostas = listOf()
    )
}