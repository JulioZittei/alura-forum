package br.com.alura.forum.service

import br.com.alura.forum.dto.CreateTopicoRequest
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.dto.UpdateTopicoRequest
import br.com.alura.forum.exception.BadRequestException
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoMapper
import br.com.alura.forum.mapper.TopicoResponseMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service



@Service
class TopicoService (
        private var topicos: List<Topico> = mutableListOf(),
        private val topicoResponseMapper: TopicoResponseMapper,
        private val topicoMapper: TopicoMapper
) {

    companion object {
        const val TOPICO_NAO_ENCONTRADO: String = "Topico não encontrado"
    }

    fun findAllTopicos(): List<TopicoResponse> {
        return topicos.map { t -> topicoResponseMapper.map(t) }
    }

    fun findTopicoById(id: Long): TopicoResponse {
        val topico = topicos.stream().filter { t ->
            t.id?.equals(id) ?: false
        }.findFirst().orElseThrow{ NotFoundException(TOPICO_NAO_ENCONTRADO)}

        return topicoResponseMapper.map(topico)
    }

    fun createTopico(topico: CreateTopicoRequest): TopicoResponse {
       try {
           topicos = topicos.plus(topicoMapper.map(topico)
               .copy(id = topicos.size.toLong() + 1))
       } catch (ex: NotFoundException) {
           throw BadRequestException(ex.message)
       }
        return topicoResponseMapper.map(topicos.last())
    }

    fun updateTopico(id: Long, topico: UpdateTopicoRequest): TopicoResponse {
        topicos = topicos.map {
            if (it.id?.equals(id) == true) {
                it.copy(
                    titulo = topico.titulo ?: it.titulo,
                    mensagem = topico.mensagem ?: it.mensagem
                )
            } else {
                it
            }
        }

        val updatedTopico = topicos.find { it.id?.equals(id) ?: false }
            ?: throw NotFoundException(TOPICO_NAO_ENCONTRADO)

        return topicoResponseMapper.map(updatedTopico)
    }

    fun deleteTopico(id: Long) {
        val topico = topicos.find { it.id?.equals(id) ?: false }
            ?: throw NotFoundException(TOPICO_NAO_ENCONTRADO)
        topicos = topicos.minus(topico)
    }
}