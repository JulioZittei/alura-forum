package br.com.alura.forum.mapper

import br.com.alura.forum.dto.CreateCursoRequest
import br.com.alura.forum.model.Curso
import org.springframework.stereotype.Component


@Component
class CursoMapper(): Mapper<CreateCursoRequest, Curso> {
    override fun map(c: CreateCursoRequest): Curso {
        return Curso(c.nome, c.categoria)
    }
}