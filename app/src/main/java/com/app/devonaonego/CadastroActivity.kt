package com.app.devonaonego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

   // private var firebaseAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        //Configurações iniciais
        //firebaseAuth = FirebaseAuth.getInstance()
    }

    fun cadastrarUsuario(view: View){
        var nome = editNomeCadastro.text.toString()
        var email = editNomeCadastro.text.toString()
        var senha = editNomeCadastro.text.toString()

        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()){
            //Fazer cadastro do usuário
            /*firebaseAuth!!.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Usuario criado com sucesso",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Erro ao criar usuario",Toast.LENGTH_SHORT).show()
                    }
                }
*/
        }else{
            Toast.makeText(this,"Preencha todos os campos",Toast.LENGTH_SHORT).show()
        }
    }
}

