package br.com.alura.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Topico(
        val titulo: String,
        val mensagem: String,
        @ManyToOne
        val curso: Curso,
        @ManyToOne
        val autor: Usuario,
        @Enumerated(EnumType.STRING)
        val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
        @OneToMany
        val respostas: List<Resposta> = ArrayList(),
        val dataCriacao: LocalDateTime = LocalDateTime.now(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)
