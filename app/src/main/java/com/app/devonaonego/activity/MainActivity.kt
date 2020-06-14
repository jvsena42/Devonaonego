package com.app.devonaonego.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.app.devonaonego.R
import com.app.devonaonego.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Configurações iniciais
        firebaseAuth = ConfiguracaoFirebase.firebaseAuth

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
}
