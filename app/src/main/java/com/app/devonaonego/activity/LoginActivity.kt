package com.app.devonaonego.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth!!

    }

    fun telaCadastro(view:View){
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }

    fun verificarUsuarioLogado(){
        if (firebaseAuth?.currentUser != null){
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
