package com.app.devonaonego.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.NonNull
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.app.devonaonego.helper.DateCustom
import com.app.devonaonego.model.Movimentacao
import com.app.devonaonego.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesa.*
import kotlinx.android.synthetic.main.activity_receita.*

class DespesaActivity : AppCompatActivity() {

    private var campoData:EditText? = null
    private var campoCategoria: Spinner? = null
    private var firebaseRef: DatabaseReference? = null
    private var usuarioRef: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var despesaTotal: Double? = 0.0
    var idUsuario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        //inicializar componentes
        campoData = findViewById(R.id.editDespesaData)
        campoCategoria = findViewById(R.id.spinnerDespesaCategoria)

        //Configurações iniciais
        campoData?.setText(DateCustom.dataAtual())
        firebaseRef = ConfiguracaoFirebase.firebaseDatabase
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth

        idUsuario = ConfiguracaoFirebase.getIdUsuario()
        usuarioRef = firebaseRef!!.child("usuarios").child(idUsuario)

        recuperarDespesaTotal()
        carregarDadosSpinner()

    }

    private fun carregarDadosSpinner() {
        val categoriaDespesas = resources.getStringArray(R.array.despesas)
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item,
            categoriaDespesas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        campoCategoria?.adapter = adapter

    }

    fun salvarDespesa(view: View){
        var data = editDespesaData.text.toString()
        var categoria = campoCategoria?.selectedItem.toString()
        var descricao = editDespesaDescricao.text.toString()
        var tipo = "d"
        var valor = editDespesaValor?.text.toString().toDouble()

        if (data.isNotEmpty() && categoria.isNotEmpty() && descricao.isNotEmpty() && tipo.isNotEmpty() && valor!=null){

            var movimentacao = Movimentacao(data,categoria,descricao,tipo,valor)

            var despesaAtualizada = valor + despesaTotal!!
            atualizarDespesaTotal(despesaAtualizada)

            movimentacao.salvar()
            Toast.makeText(this,"Despesa salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()

        }else{
            Toast.makeText(this,"Preencha todos os valores", Toast.LENGTH_SHORT).show()
        }

    }

    fun recuperarDespesaTotal(){

        usuarioRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                despesaTotal = usuario?.despesaTotal
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })
    }

    fun atualizarDespesaTotal(despesa: Double){
        usuarioRef!!.child("despesaTotal").setValue(despesa)
    }
}
