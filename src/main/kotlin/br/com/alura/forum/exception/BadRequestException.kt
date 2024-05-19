package br.com.alura.forum.exception

import java.lang.RuntimeException

class BadRequestException(message: String?): RuntimeException(message)