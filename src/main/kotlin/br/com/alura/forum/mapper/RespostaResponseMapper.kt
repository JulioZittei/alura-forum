package br.com.alura.forum.mapper

import br.com.alura.forum.dto.RespostaResponse
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaResponseMapper (
    private val topicoService: TopicoService,
    private val usuarioService: UsuarioService
): Mapper<Resposta, RespostaResponse> {

    override fun map(r: Resposta): RespostaResponse {
        return RespostaResponse(
           r.mensagem,
            r.autor.id!!,
            r.topico.id!!,
            r.solucao,
            r.dataCriacao,
            r.id!!
        )
    }
}