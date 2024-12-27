package br.com.alura.forum.util

abstract class Constants {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_TOKEN = "Bearer "

        const val TOPICOS_PATH = "/topicos/**"
        const val RESPOSTAS_PATH = "/respostas/**"
        const val RELATORIOS_PATH = "/relatorios/**"
    }

}