package br.com.alura.forum.service

import br.com.alura.forum.dto.CreateCursoRequest
import br.com.alura.forum.dto.CursoResponse
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.CursoMapper
import br.com.alura.forum.mapper.CursoResponseMapper
import br.com.alura.forum.model.Curso
import br.com.alura.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService (
    private val cursoRepository: CursoRepository,
    private val cursoResponseMapper: CursoResponseMapper,
    private val cursoMapper: CursoMapper
) {

    companion object {
        const val CURSO_NAO_ENCONTRADO: String = "Curso não encontrado"
    }

    fun findAll(): List<Curso> {
        return cursoRepository.findAll()
    }

    fun findById(id: Long): Curso {
        return cursoRepository.findById(id).orElseThrow{NotFoundException(CURSO_NAO_ENCONTRADO)}
    }

    fun findAllCursos(): List<CursoResponse> {
        return cursoRepository.findAll().map {c -> cursoResponseMapper.map(c)}
    }

    fun findCursoById(id: Long): CursoResponse {
        val existingCurso = cursoRepository.findById(id).orElseThrow{NotFoundException(CURSO_NAO_ENCONTRADO)}
        return cursoResponseMapper.map(existingCurso)
    }


    fun createCurso(curso: CreateCursoRequest): CursoResponse {
        val createdCurso = cursoRepository.save(cursoMapper.map(curso))
        return cursoResponseMapper.map(createdCurso)
    }
}
