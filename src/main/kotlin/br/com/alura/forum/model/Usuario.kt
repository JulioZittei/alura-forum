package br.com.alura.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Usuario (
        val nome: String,
        val email: String,
        val password: String,

        @JsonIgnore
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "usuario_role",
                joinColumns = [JoinColumn(name = "usuario_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val roles: List<Role> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)
