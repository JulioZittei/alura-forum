package br.com.alura.forum.exception

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.*
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI
import java.time.LocalDateTime


@RestControllerAdvice
class ExceptionHandler(
    private val messageSource: MessageSource
): ResponseEntityExceptionHandler() {

    companion object {
        const val INVALID_PARAMS: String = "invalidParams"
        const val TIMESTAMP: String = "timestamp"
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException, request: WebRequest): ResponseEntity<Any>? {
        val httpStatus = HttpStatus.NOT_FOUND
        val problemDetail = ProblemDetail.forStatus(httpStatus).also {
            it.title = httpStatus.reasonPhrase
            it.detail = ex.message
            it.instance = URI((request as ServletWebRequest).request.requestURI)
            it.setProperty(TIMESTAMP, LocalDateTime.now())
        }
        return handleExceptionInternal(ex, problemDetail, HttpHeaders(), httpStatus, request)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException, request: WebRequest): ResponseEntity<Any>? {
        val httpStatus = HttpStatus.BAD_REQUEST
        val problemDetail = ProblemDetail.forStatus(httpStatus).also {
            it.title = httpStatus.reasonPhrase
            it.detail = ex.message
            it.instance = URI((request as ServletWebRequest).request.requestURI)
            it.setProperty(TIMESTAMP, LocalDateTime.now())
        }
        return handleExceptionInternal(ex, problemDetail, HttpHeaders(), httpStatus, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleBadRequest(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        val problemDetail = ProblemDetail.forStatus(httpStatus).also {
            it.title = httpStatus.reasonPhrase
            it.detail = ex.message
            it.instance = URI((request as ServletWebRequest).request.requestURI)
            it.setProperty(TIMESTAMP, LocalDateTime.now())
        }
        return handleExceptionInternal(ex, problemDetail, HttpHeaders(), httpStatus, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val httpStatus = HttpStatus.UNPROCESSABLE_ENTITY
        val invalidParams: MutableList<Map<String, Any>> = ArrayList()

        for (error in ex.bindingResult.allErrors) {
            val invalidParam: MutableMap<String, Any> = HashMap()
            val message = messageSource.getMessage(error, LocaleContextHolder.getLocale())
            val field = (error as FieldError).field
            invalidParam["name"] = field
            invalidParam["message"] = message
            invalidParams.add(invalidParam)
        }
        val problemDetail = ProblemDetail.forStatus(httpStatus).also {
            it.title = httpStatus.reasonPhrase
            it.detail = "Verifique os parâmetros enviados e tente novamente"
            it.setProperty(INVALID_PARAMS, invalidParams)
            it.instance = URI((request as ServletWebRequest).request.requestURI)
            it.setProperty(TIMESTAMP, LocalDateTime.now())
        }

        return handleExceptionInternal(ex, problemDetail, HttpHeaders(), httpStatus, request)
    }
}