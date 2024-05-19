package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Curso
import org.springframework.stereotype.Service

@Service
class CursoService (
    private var cursos: List<Curso> = mutableListOf()
) {

    companion object {
        const val CURSO_NAO_ENCONTRADO: String = "Curso não encontrado"
    }

    init {
        cursos = mutableListOf(
            Curso(1, "Kotlin", "Programação")
        )
    }

    fun findAllCursos(): List<Curso> {
        return cursos
    }

    fun findCursoById(id: Long): Curso {
        return cursos.find { c -> c.id?.equals(id) ?: false } ?: throw NotFoundException(CURSO_NAO_ENCONTRADO)
    }
}
