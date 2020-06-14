package com.app.devonaonego.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


object ConfiguracaoFirebase {

    private var referenciaFirebase: DatabaseReference? = null
    private var referenciaAutenticacao: FirebaseAuth? = null
    private var referenciaStorage: StorageReference? = null

    //retorna a referencia do Database
    val firebaseDatabase: DatabaseReference
        get() {
            if (referenciaFirebase == null) {
                referenciaFirebase = FirebaseDatabase.getInstance().reference
            }
            return referenciaFirebase!!
        }

    //retorna a instancia do FirebaseAuth
    val firebaseAuth: FirebaseAuth?
        get() {
            if (referenciaAutenticacao == null) {
                referenciaAutenticacao = FirebaseAuth.getInstance()
            }
            return referenciaAutenticacao
        }

    //Retorna instancia do FirebaseStorage
    val firebaseStorage: StorageReference
        get() {
            if (referenciaStorage == null) {
                referenciaStorage = FirebaseStorage.getInstance().reference
            }
            return referenciaStorage!!
        }

}
