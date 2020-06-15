package com.app.devonaonego.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
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


    //Firebase
    private var firebaseAuth: FirebaseAuth? = null
    private var databaseRef: DatabaseReference? = null
    private var usuarioRef: DatabaseReference? = null
    private var valueEventListenerUser: ValueEventListener? = null
    private var idUsuario: String = ""

    //Datas
    private var calendarView:MaterialCalendarView? = null
    private var mesAnoSelecionado: String = ""

    //Valores
    private var nome:String = ""
    private var despesaTotal: Double = 0.0
    private var receitaTotal: Double = 0.0
    private var saldo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar = toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        //inicializar componentes
        calendarView = findViewById(R.id.calendarViewId)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth
        databaseRef = ConfiguracaoFirebase.firebaseDatabase
        idUsuario = ConfiguracaoFirebase.getIdUsuario()
        usuarioRef = databaseRef!!.child("usuarios").child(idUsuario)


        configuraCalendarView()

    }

    override fun onStart() {
        super.onStart()
        recuperarDadosUsuario()
    }

    fun recuperarDadosUsuario(){

        valueEventListenerUser = usuarioRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)
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

    fun atualizarDados(){
        textResumoSaldacao.text = "Bem-vindo, $nome!"
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
        var mesSelecionado = String.format("%02d", dataAtual?.month?.plus(1))
        mesAnoSelecionado = mesSelecionado + "" + dataAtual?.year

        calendarView?.setOnMonthChangedListener { widget, date ->
            var mesSelecionado = String.format("%02d", date.month + 1)
            mesAnoSelecionado = mesSelecionado + "" + date.year

            //movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
            //recuperarMovimentacoes();
        }
    }

    override fun onStop() {
        super.onStop()
        usuarioRef!!.removeEventListener(this.valueEventListenerUser!!)
    }
}

