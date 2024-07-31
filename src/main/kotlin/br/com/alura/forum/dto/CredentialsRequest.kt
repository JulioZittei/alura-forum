package br.com.alura.forum.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CredentialsRequest @JsonCreator constructor(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("password")
    val password: String
) {

}
