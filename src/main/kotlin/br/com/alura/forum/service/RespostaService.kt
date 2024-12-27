package br.com.alura.forum.service

import br.com.alura.forum.dto.CreateRespostaRequest
import br.com.alura.forum.dto.RespostaResponse
import br.com.alura.forum.exception.BadRequestException
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.RespostaMapper
import br.com.alura.forum.mapper.RespostaResponseMapper
import br.com.alura.forum.repository.RespostaRepository
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private val respostaRepository: RespostaRepository,
    private val respostaMapper: RespostaMapper,
    private val respostaResponseMapper: RespostaResponseMapper,
    private val emailService: EmailService
) {

    companion object {
        const val RESPOSTA_NAO_ENCONTRADA: String = "Resposta não encontrada"
    }

    fun createResposta(resposta: CreateRespostaRequest): RespostaResponse {
        try {
            val createdResposta = respostaRepository.save(respostaMapper.map(resposta))
            emailService.notify(createdResposta.autor.nome, createdResposta.topico.autor.email, createdResposta.topico.titulo)
            return respostaResponseMapper.map(createdResposta)
        } catch (ex: NotFoundException) {
            throw BadRequestException(ex.message)
        }
    }

    fun findRespostaById(id: Long): RespostaResponse {
        val topico = respostaRepository.findById(id).orElseThrow{NotFoundException(RESPOSTA_NAO_ENCONTRADA)}
        return respostaResponseMapper.map(topico)
    }

    fun deleteResposta(id: Long) {
        findRespostaById(id)
        respostaRepository.deleteById(id)
    }

}