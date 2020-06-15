package com.app.devonaonego.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesa.*
import kotlinx.android.synthetic.main.activity_receita.*

class ReceitaActivity : AppCompatActivity() {

    private var campoData: EditText? = null
    private var campoCategoria: Spinner? = null
    private var firebaseRef: DatabaseReference? = null
    private var usuarioRef: DatabaseReference? = null
    private var receitaTotal: Double? = 0.0
    private var idUsuario = ""
    private var valueEventListenerReceita: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        //inicializar componentes
        campoData = findViewById(R.id.editReceitaData)
        campoCategoria = findViewById(R.id.spinnerReceitaCategoria)

        //Configurações iniciais
        campoData?.setText(DateCustom.dataAtual())
        firebaseRef = ConfiguracaoFirebase.firebaseDatabase

        idUsuario = ConfiguracaoFirebase.getIdUsuario()
        usuarioRef = firebaseRef!!.child("usuarios").child(idUsuario)

        carregarDadosSpinner()
        recuperarReceitaTotal()
    }

    private fun carregarDadosSpinner() {
        val categoriaReceitas = resources.getStringArray(R.array.receitas)
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item,
            categoriaReceitas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        campoCategoria?.adapter = adapter

    }

    fun salvarReceita(view: View){
        var data = editReceitaData.text.toString()
        var categoria = spinnerReceitaCategoria?.selectedItem.toString()
        var descricao = editReceitaDescricao.text.toString()
        var tipo = "r"
        var valor = editReceitaValor?.text.toString().toDouble()

        if (data.isNotEmpty() && categoria.isNotEmpty() && descricao.isNotEmpty() && tipo.isNotEmpty() && valor!=null){

            var movimentacao = Movimentacao(data,categoria,descricao,tipo,valor,idUsuario)

            var receitaAtualizada = valor + receitaTotal!!
            atualizarReceitaTotal(receitaAtualizada)

            movimentacao.salvar()
            Toast.makeText(this,"Receita salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()

        }else{
            Toast.makeText(this,"Preencha todos os valores", Toast.LENGTH_SHORT).show()
        }

    }

    fun recuperarReceitaTotal(){

        valueEventListenerReceita = usuarioRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                receitaTotal = usuario?.receitaTotal
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })
    }

    fun atualizarReceitaTotal(receita: Double){
        usuarioRef!!.child("receitaTotal").setValue(receita)
    }

    override fun onStop() {
        super.onStop()
        usuarioRef!!.removeEventListener(this.valueEventListenerReceita!!)
    }
}
