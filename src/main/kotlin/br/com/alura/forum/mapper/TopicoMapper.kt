package br.com.alura.forum.mapper

import br.com.alura.forum.dto.CreateTopicoRequest
import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.CursoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
): Mapper<CreateTopicoRequest, Topico> {

    override fun map(t: CreateTopicoRequest): Topico {
        return Topico(
            t.titulo!!,
            t.mensagem!!,
            cursoService.findById(t.idCurso!!),
            usuarioService.findById(t.idUsuario!!)
        )
    }
}