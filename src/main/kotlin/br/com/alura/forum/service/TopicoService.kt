package br.com.alura.forum.service

import br.com.alura.forum.dto.CreateTopicoRequest
import br.com.alura.forum.dto.TopicoReport
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.dto.UpdateTopicoRequest
import br.com.alura.forum.exception.BadRequestException
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoMapper
import br.com.alura.forum.mapper.TopicoResponseMapper
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class TopicoService (
        private val topicoRepository: TopicoRepository,
        private val topicoResponseMapper: TopicoResponseMapper,
        private val topicoMapper: TopicoMapper
) {

    companion object {
        const val TOPICO_NAO_ENCONTRADO: String = "Topico não encontrado"
    }

    fun findAllTopicos(curso: String?, pagination: Pageable): Page<TopicoResponse> {
        return topicoRepository.findByCursoNomeContainingIgnoreCase(curso ?: "", pagination).map { t -> topicoResponseMapper.map(t) }
    }

    fun findTopicoById(id: Long): TopicoResponse {
        val topico = topicoRepository.findById(id).orElseThrow{NotFoundException(TOPICO_NAO_ENCONTRADO)}
        return topicoResponseMapper.map(topico)
    }

    fun createTopico(topico: CreateTopicoRequest): TopicoResponse {
       try {
           val createdTopico = topicoRepository.save(topicoMapper.map(topico))
           return topicoResponseMapper.map(createdTopico)
       } catch (ex: NotFoundException) {
           throw BadRequestException(ex.message)
       }
    }

    fun updateTopico(id: Long, topico: UpdateTopicoRequest): TopicoResponse {
        val existingTopico = topicoRepository.findById(id).orElseThrow {NotFoundException(TOPICO_NAO_ENCONTRADO)}
        val updatedTopico = topicoRepository.save(existingTopico.copy(
            titulo = topico.titulo!!,
            mensagem = topico.mensagem!!
        ))
        return topicoResponseMapper.map(updatedTopico)
    }

    fun deleteTopico(id: Long) {
        findTopicoById(id)
        topicoRepository.deleteById(id)
    }

    fun report(): List<TopicoReport> {
        return topicoRepository.report()
    }
}