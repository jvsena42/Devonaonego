package com.app.devonaonego.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.app.devonaonego.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    var nome: String = ""
    var email: String = ""
    var senha: String = ""

    //Referências banco de dados
    private var mDatabase: DatabaseReference? = null
    private var userRef: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        //Configurações iniciais
        mAuth = ConfiguracaoFirebase.firebaseAuth
        mDatabase = ConfiguracaoFirebase.firebaseDatabase
        userRef = mDatabase!!.child("usuarios")
    }

    fun cadastrarUsuario(view: View){
        nome = editNomeCadastro.text.toString()
        email = editEmailCadastro.text.toString()
        senha = editSenhaCadastro.text.toString()

        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()){

            //Fazer cadastro do usuário
            mAuth!!.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this){task ->
                if (task.isSuccessful){

                    val userId = mAuth!!.currentUser!!.uid
                    val usuario = Usuario(nome,email,userId)
                    usuario.salvar()
                    abrirTelaPrincipal()
                    finish()

                    Toast.makeText(this,"Usuario criado com sucesso!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Erro ao criar usuário!",Toast.LENGTH_SHORT).show()
                }
            }


        }else{
            Toast.makeText(this,"Preencha todos os campos",Toast.LENGTH_SHORT).show()
        }
    }

    fun abrirTelaPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

