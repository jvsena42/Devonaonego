package com.app.devonaonego.model

import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.app.devonaonego.helper.DateCustom
import com.google.firebase.database.DatabaseReference

data class Movimentacao(
    var data: String = "",
    var categoria: String = "",
    var descricao: String = "",
    var tipo: String,
    var valor: Double,
    var idUsuario: String = "",
    var idMovimentacao: String = ""


) {

    constructor() : this("", "","","",0.0,"","")

    fun salvar() {

        var databaseReference = ConfiguracaoFirebase.firebaseDatabase
        idMovimentacao =databaseReference.child("movimentacoes").child(idUsuario).child(DateCustom.mesAnoDataEscolhida(data)).push().key.toString()

        databaseReference.child("movimentacoes")
            .child(idUsuario)
            .child(DateCustom.mesAnoDataEscolhida(data))
            .child(idMovimentacao)
            .setValue(this)
    }
}