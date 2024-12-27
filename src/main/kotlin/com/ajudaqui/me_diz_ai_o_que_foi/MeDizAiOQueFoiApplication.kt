package com.ajudaqui.me_diz_ai_o_que_foi

import com.ajudaqui.me_diz_ai_o_que_foi.entity.Pessoa
import com.ajudaqui.me_diz_ai_o_que_foi.producer.PessoaProducerImp
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MeDizAiOQueFoiApplication(val pessoaProducer: PessoaProducerImp) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val pessoa = Pessoa(name = "Bilbu", lastName = " bolseiro")
        pessoaProducer.persist("12345", pessoa)
    }

}

fun main(args: Array<String>) {
    runApplication<MeDizAiOQueFoiApplication>(*args)
}
