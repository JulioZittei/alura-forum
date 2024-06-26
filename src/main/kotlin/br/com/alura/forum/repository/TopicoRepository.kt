package br.com.alura.forum.repository

import br.com.alura.forum.dto.TopicoReport
import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TopicoRepository: JpaRepository<Topico, Long> {

    fun findByCursoNomeContainingIgnoreCase(nome: String, pagination: Pageable): Page<Topico>

    @Query("SELECT new br.com.alura.forum.dto.TopicoReport(curso.categoria, count(t)) from Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun report(): List<TopicoReport>
}