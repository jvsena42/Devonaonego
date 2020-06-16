package com.app.devonaonego.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.devonaonego.R
import com.app.devonaonego.adapter.AdapterMovimentacao
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.app.devonaonego.model.Movimentacao
import com.app.devonaonego.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {


    //Datas
    private var calendarView:MaterialCalendarView? = null
    private var mesAnoSelecionado: String = ""

    //Valores
    private var nome:String = ""
    private var despesaTotal: Double = 0.0
    private var receitaTotal: Double = 0.0
    private var saldo: String = ""

    //Objetos
    private var recyclerView: RecyclerView? = null
    //private var adapterMovimentacao: AdapterMovimentacao? = null
    private var adapterMov: AdapterMovimentacao? = null
    private val movimentacoes: MutableList<Movimentacao> = mutableListOf<Movimentacao>()

    //Firebase
    private var firebaseAuth: FirebaseAuth? = null
    private var databaseRef: DatabaseReference? = null
    private var usuarioRef: DatabaseReference? = null
    private var movimentacaoRef: DatabaseReference? = null
    private var valueEventListenerUser: ValueEventListener? = null
    private var valueEventListenerMovimentacao: ValueEventListener? = null
    private var idUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar = toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        //inicializar componentes
        calendarView = findViewById(R.id.calendarViewId)
        recyclerView = findViewById(R.id.recyclerResumoMovimentos)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth
        idUsuario = ConfiguracaoFirebase.getIdUsuario()
        databaseRef = ConfiguracaoFirebase.firebaseDatabase

        configuraCalendarView()

        //Configurar adapter
        adapterMov = AdapterMovimentacao(movimentacoes, this)

        //Configurar Recyclerview
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapterMov

    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUsuario()
        recuperarMovimentacoes()
    }

    fun recuperarDadosUsuario(){

        usuarioRef = databaseRef!!.child("usuarios").child(idUsuario)
        valueEventListenerUser = usuarioRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                var usuario = dataSnapshot.getValue(Usuario::class.java)
                nome = usuario!!.nome
                receitaTotal = usuario.receitaTotal
                despesaTotal = usuario.despesaTotal
                var direfenca= receitaTotal-despesaTotal

                var decimalFormat = DecimalFormat("0.00")
                saldo = decimalFormat.format(direfenca)

                atualizarDados()
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })

    }

    fun recuperarMovimentacoes(){

        movimentacaoRef = databaseRef!!.child("movimentacoes").child(idUsuario).child(mesAnoSelecionado)

        Log.i("MES","mes: $mesAnoSelecionado")

        valueEventListenerMovimentacao = movimentacaoRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {

                movimentacoes.clear()
                for (dados:DataSnapshot in dataSnapshot.children) {
                    Log.i("DADOS","dados: $dados")
                    var movimentacao: Movimentacao? = dados.getValue(Movimentacao::class.java)
                    if (movimentacao != null) {
                        movimentacoes.add(movimentacao)
                    }
                }
                adapterMov!!.notifyDataSetChanged()
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {

            }
        })
    }

    fun atualizarDados(){
        textResumoSaldacao.text = "Olá, $nome!"
        textResumoSaldo.text = "R$ $saldo"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.menu_sair){
            firebaseAuth?.signOut()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun telaReceita(view:View){
        var intent = Intent(this,ReceitaActivity::class.java)
        startActivity(intent)
    }

    fun telaDespesa (view:View){
        var intent = Intent(this,DespesaActivity::class.java)
        startActivity(intent)
    }

    private fun configuraCalendarView() {
        val meses = arrayOf<CharSequence>(
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
        )
        calendarView?.setTitleMonths(meses)

        var dataAtual: CalendarDay? = calendarView?.getCurrentDate()
        var mesSelecionado =String.format("%02d",dataAtual!!.month+1)
        mesAnoSelecionado = mesSelecionado + "" + dataAtual.year


        calendarView?.setOnMonthChangedListener { widget, date ->

            var mesSelecionado2 =String.format("%02d",date!!.month+1)
            mesAnoSelecionado = mesSelecionado2 + "" + date.year

            movimentacaoRef!!.removeEventListener(this.valueEventListenerMovimentacao!!)
            recuperarMovimentacoes()
        }
    }

    override fun onStop() {
        super.onStop()
        usuarioRef!!.removeEventListener(this.valueEventListenerUser!!)
        movimentacaoRef!!.removeEventListener(this.valueEventListenerMovimentacao!!)
    }
}

