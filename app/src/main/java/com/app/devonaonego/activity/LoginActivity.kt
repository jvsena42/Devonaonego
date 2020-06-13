package com.app.devonaonego.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.devonaonego.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun telaCadastro(view:View){
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }
}
