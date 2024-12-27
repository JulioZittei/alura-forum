package br.com.alura.forum.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {

    fun notify(nomeAutorResposta: String, emailAutorTopico: String, tituloTopico: String) {
        val message = SimpleMailMessage()
        message.subject = "[ALURA] RESPOSTA RECEBIDA PARA O TOPICO `${tituloTopico}`"
        message.text = "Olá, seu tópico foi respondido por ${nomeAutorResposta}. Vamos lá conferir?"
        message.setTo(emailAutorTopico)

        javaMailSender.send(message)
    }
}