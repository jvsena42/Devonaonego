package com.app.devonaonego.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null
    var email: String = ""
    var senha: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth!!

    }

    fun validarLogin(view: View){
        email = editLoginEmail.text.toString()
        senha = editLoginSenha.text.toString()

        if (email.isNotEmpty() && senha.isNotEmpty()){
            firebaseAuth?.signInWithEmailAndPassword(email,senha)?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    abrirTelaPrincipal()
                }else{
                    Toast.makeText(this,"Erro ao fazer login",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"Preencha todos os campos!",Toast.LENGTH_SHORT).show()
        }
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
            abrirTelaPrincipal()
        }

    }

    fun abrirTelaPrincipal(){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
