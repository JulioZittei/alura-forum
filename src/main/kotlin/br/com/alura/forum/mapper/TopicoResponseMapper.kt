package br.com.alura.forum.mapper

import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoResponseMapper(): Mapper<Topico, TopicoResponse> {

    override fun map(t: Topico): TopicoResponse {
        return TopicoResponse(
            t.id!!,
            t.titulo,
            t.mensagem,
            t.status,
            t.dataCriacao
        )
    }
}