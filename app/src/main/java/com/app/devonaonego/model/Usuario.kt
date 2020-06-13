package com.app.devonaonego.model

import com.app.devonaonego.helper.ConfiguracaoFirebase

data class Usuario (var nome: String ="",var email: String ="", var id: String = "") {

    fun salvar() {
        val usuarioRef = ConfiguracaoFirebase.firebaseDatabase
            .child("usuarios")
            .child(id)
        usuarioRef.setValue(this)
    }
}
