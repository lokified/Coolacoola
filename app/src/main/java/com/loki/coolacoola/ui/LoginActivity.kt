package com.loki.coolacoola.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.loki.coolacoola.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mSignUp = findViewById(R.id.signup_btn)

        mSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}