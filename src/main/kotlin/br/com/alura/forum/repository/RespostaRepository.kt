package br.com.alura.forum.repository

import br.com.alura.forum.model.Resposta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RespostaRepository: JpaRepository<Resposta, Long> {
}