package com.ajudaqui.me_diz_ai_o_que_foi.consumer

import com.ajudaqui.me_diz_ai_o_que_foi.entity.Pessoa
import com.ajudaqui.me_diz_ai_o_que_foi.entity.PessoaDTO
import com.ajudaqui.me_diz_ai_o_que_foi.producer.PessoaProducerImp
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PessoaConsumerImp {
    private val logger: Logger = LoggerFactory.getLogger(PessoaConsumerImp::class.java)

    //criar um ouvinte do kafka
//    @KafkaListener(topics = ["pessoa_01"], groupId = "pessoa-consumer")// para ouvir a ultima

@KafkaListener(id = "pessoa-consumer", // para ouvir todas
    topicPartitions = [
        TopicPartition(
            topic = "pessoa_01",
            partitions = ["0"],// pegando a primeira
            partitionOffsets = arrayOf(PartitionOffset(partition = "*", initialOffset = "0"))// lendo pratir de todas
        )
    ])
    fun consumer(@Payload pessoaDTO: PessoaDTO){

        val pessoa= Pessoa(pessoaDTO.getName().toString(), pessoaDTO.getLastName().toString())
        logger.info("Pessoa recebida: ${pessoa.toString()}")
    }

}