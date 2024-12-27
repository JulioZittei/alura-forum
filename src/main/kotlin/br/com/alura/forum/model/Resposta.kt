package br.com.alura.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "resposta")
data class Resposta (
        val mensagem: String,
        @ManyToOne
        val autor: Usuario,
        @ManyToOne
        val topico: Topico,
        val solucao: Boolean,
        val dataCriacao: LocalDateTime = LocalDateTime.now(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)
