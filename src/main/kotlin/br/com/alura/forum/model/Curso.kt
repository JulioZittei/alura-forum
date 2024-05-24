package br.com.alura.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Curso (
        val nome: String,
        val categoria: String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)