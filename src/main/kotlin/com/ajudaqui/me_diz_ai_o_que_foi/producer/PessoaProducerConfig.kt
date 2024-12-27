package com.ajudaqui.me_diz_ai_o_que_foi.producer

import com.ajudaqui.me_diz_ai_o_que_foi.entity.PessoaDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class PessoaProducerConfig {

    @Bean
    fun pessoaDTOTemplate(factor: ProducerFactory<String, PessoaDTO>): KafkaTemplate<String, PessoaDTO> =
        KafkaTemplate(factor)
}