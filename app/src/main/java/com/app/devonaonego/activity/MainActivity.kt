package com.app.devonaonego.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null

    var calendarView:MaterialCalendarView? = null
    var mesAnoSelecionado: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar = toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth

        //inicializar componentes
        calendarView = findViewById(R.id.calendarViewId)

        configuraCalendarView()

        fab.setOnClickListener { view ->

        }
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

    fun configuraCalendarView() {
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
}

