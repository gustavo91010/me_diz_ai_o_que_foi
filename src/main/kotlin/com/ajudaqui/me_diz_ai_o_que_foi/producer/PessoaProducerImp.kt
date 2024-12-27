package com.ajudaqui.me_diz_ai_o_que_foi.producer

import com.ajudaqui.me_diz_ai_o_que_foi.entity.Pessoa
import com.ajudaqui.me_diz_ai_o_que_foi.entity.PessoaDTO
import com.google.common.util.concurrent.ListenableFuture
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.concurrent.CompletableFuture

@Component
class PessoaProducerImp(
    private val pessoaTemplate: KafkaTemplate<String, PessoaDTO>
) {

    val topicName = "pessoa_01"

    fun persist(messageId: String, payload: Pessoa) {
        val dto: PessoaDTO = createDTO(payload)
        sendPessoaMessage(messageId, dto)
    }
//envio, espero e analiso a mensagem
    private fun sendPessoaMessage(messageId: String, dto: PessoaDTO) {
        val message = createMessageWithHeaders(messageId, dto, topicName)

        val future: CompletableFuture<SendResult<String, PessoaDTO>> = pessoaTemplate.send(message)

        future.whenComplete { result, ex ->
            if (ex == null) {
                println("Pessoa enviada com sucesso. MessageId: $messageId")
            } else {
                println("Erro no envio. MessageId: $messageId, erro: ${ex.message}")
            }
        }
    }

    //
    private fun createDTO(payload: Pessoa): PessoaDTO =
        PessoaDTO.newBuilder()
            .setName(payload.name)
            .setLastName(payload.lastName).build()

    private fun createMessageWithHeaders(messageId: String, pessoaDTO: PessoaDTO, topic: String):
            Message<PessoaDTO> {
        return MessageBuilder.withPayload(pessoaDTO)
            .setHeader("hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1L))
            .setHeader("type", "fct")
            .setHeader("cid", messageId)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.KEY, messageId)
            .build()
    }
}