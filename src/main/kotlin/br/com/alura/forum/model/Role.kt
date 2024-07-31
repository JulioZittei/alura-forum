package br.com.alura.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
data class Role(
    @JsonIgnore
    val nome: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    val usuarios: List<Usuario>,

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
): GrantedAuthority {
    override fun getAuthority() = nome

}