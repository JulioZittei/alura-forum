package br.com.alura.forum.mapper

import br.com.alura.forum.dto.CursoResponse
import br.com.alura.forum.model.Curso
import org.springframework.stereotype.Component

@Component
class CursoResponseMapper(): Mapper<Curso, CursoResponse> {
    override fun map(c: Curso): CursoResponse {
        return CursoResponse(c.id!!, c.nome, c.categoria)
    }
}