package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoMapper
import br.com.alura.forum.mapper.TopicoResponseMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.repository.TopicoRepository
import br.com.alura.forum.service.TopicoService.Companion.TOPICO_NAO_ENCONTRADO
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    val topico = TopicoTest.build()
    val topicos = PageImpl(listOf(topico))
    val paginacao: Pageable = mockk()
    val nomeCurso = "kotlin"
    val idTopico = 1L


    val cursoService: CursoService = mockk()
    val usuarioService: UsuarioService = mockk()
    val topicoRepository: TopicoRepository = mockk {
        every { findByCursoNomeContainingIgnoreCase(nomeCurso, paginacao) } returns topicos
        every { findAll(paginacao) } returns topicos
        every { findById(idTopico) } returns Optional.of(topico)
    }
    val topicoResponseMapper: TopicoResponseMapper = spyk(TopicoResponseMapper())
    val topicoMapper: TopicoMapper = TopicoMapper(cursoService, usuarioService)

    val topicoService = TopicoService(
        topicoRepository, topicoResponseMapper, topicoMapper
    )

    @Test
    fun `deve listar topicos pelo nome de um curso com sucesso`() {
        val result = topicoService.findAllTopicos(nomeCurso, paginacao)

        verify(exactly = 1) { topicoRepository.findByCursoNomeContainingIgnoreCase(nomeCurso, paginacao) }
        verify(exactly = 1) { topicoResponseMapper.map(topico) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
        assertThat(result).isNotNull
        assertThat(result.totalPages).isEqualTo(1)
        assertThat(result.content[0].id).isEqualTo(topico.id)
        assertThat(result.content[0].titulo).isEqualTo(topico.titulo)
        assertThat(result.content[0].mensagem).isEqualTo(topico.mensagem)
        assertThat(result.content[0].status).isEqualTo(topico.status)
        assertThat(result.content[0].dataCriacao).isEqualTo(topico.dataCriacao)
        assertThat(result.content[0].dataAlteracao).isEqualTo(topico.dataAlteracao)
    }

    @Test
    fun `deve listar topicos com sucesso quando o nome do curso for nulo`() {
        val result = topicoService.findAllTopicos(null, paginacao)

        verify(exactly = 0) { topicoRepository.findByCursoNomeContainingIgnoreCase(nomeCurso, paginacao) }
        verify(exactly = 1) { topicoResponseMapper.map(topico) }
        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
        assertThat(result).isNotNull
        assertThat(result.totalPages).isEqualTo(1)
        assertThat(result.content[0].id).isEqualTo(topico.id)
        assertThat(result.content[0].titulo).isEqualTo(topico.titulo)
        assertThat(result.content[0].mensagem).isEqualTo(topico.mensagem)
        assertThat(result.content[0].status).isEqualTo(topico.status)
        assertThat(result.content[0].dataCriacao).isEqualTo(topico.dataCriacao)
        assertThat(result.content[0].dataAlteracao).isEqualTo(topico.dataAlteracao)
    }

    @Test
    fun `deve lancar NotFoundException quando o topico nao for encontrado`() {
        val idTopicoInexistente = 5L
        every { topicoRepository.findById(idTopicoInexistente) } returns Optional.empty()
        val result = assertThrows<NotFoundException> { topicoService.findTopicoById(idTopicoInexistente) }

        verify(exactly = 1) { topicoRepository.findById(idTopicoInexistente) }
        verify(exactly = 0) { topicoResponseMapper.map(topico) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(NotFoundException::class.java)
        assertThat(result.message).isEqualTo(TOPICO_NAO_ENCONTRADO)
    }

}