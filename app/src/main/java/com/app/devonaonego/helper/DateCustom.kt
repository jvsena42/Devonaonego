package com.app.devonaonego.helper

import java.text.SimpleDateFormat

object DateCustom {

    fun dataAtual(): String {

        val data = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(data)
    }

    fun mesAnoDataEscolhida(data: String): String {

        val retornoData = data.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val dia = retornoData[0]
        val mes = retornoData[1]
        val ano = retornoData[2]

        return mes + ano
    }
}
