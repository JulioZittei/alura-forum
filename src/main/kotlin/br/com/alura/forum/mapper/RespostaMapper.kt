package br.com.alura.forum.mapper

import br.com.alura.forum.dto.CreateRespostaRequest
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaMapper (
    private val topicoService: TopicoService,
    private val usuarioService: UsuarioService
): Mapper<CreateRespostaRequest, Resposta> {

    override fun map(r: CreateRespostaRequest): Resposta {
        return Resposta(
            r.mensagem,
            usuarioService.findById(r.idAutor),
            topicoService.findById(r.idTopico),
            r.solucao
        )
    }
}