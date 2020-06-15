package com.app.devonaonego.model

import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.app.devonaonego.helper.DateCustom
import com.google.firebase.database.DatabaseReference

data class Movimentacao (var data: String = "",
                         var categoria: String = "",
                         var descricao: String ="",
                         var tipo:String,
                         var valor:Double,
                         var idUsuario:String="") {

    fun salvar(){
        idUsuario = ConfiguracaoFirebase.getIdUsuario()
        var databaseReference = ConfiguracaoFirebase.firebaseDatabase

        databaseReference.child("movimentacoes")
                            .child(idUsuario)
                            .child(DateCustom.mesAnoDataEscolhida(data))
                            .push()
                            .setValue(this)
    }
}