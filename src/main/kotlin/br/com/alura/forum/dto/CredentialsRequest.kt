package br.com.alura.forum.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CredentialsRequest @JsonCreator constructor(
    @JsonProperty("username")
    val username: String,
    @JsonProperty("password")
    val password: String
) {

}
