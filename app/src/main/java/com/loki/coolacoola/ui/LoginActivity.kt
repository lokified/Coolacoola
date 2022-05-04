package com.loki.coolacoola.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.loki.coolacoola.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mSignUp: Button
    private lateinit var mLogin : Button
    private lateinit var mEmail : TextInputEditText
    private lateinit var mPassword : TextInputEditText
    private lateinit var mProgress : ProgressBar

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val user : FirebaseUser? = it.currentUser

            if (user != null) {
                Intent(this, MainActivity::class.java).also {intent ->
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        }

        mProgress = findViewById(R.id.login_progress)
        mEmail = findViewById(R.id.et_email)
        mPassword = findViewById(R.id.et_password)
        mLogin = findViewById(R.id.signin_btn)
        mSignUp = findViewById(R.id.signup_btn)

        mSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

        mLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {

        mProgress.visibility = View.VISIBLE

        val email : String = mEmail.text.toString()
        val password : String = mPassword.text.toString()


        if (email.isEmpty()) {
            mEmail.error = "Enter Email"
            return
        }

        if (password.isEmpty()) {
            mPassword.error = "Enter password"
            return
        }


        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
                else{
                    Intent(this, MainActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                        mProgress.visibility = View.GONE
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()

        mAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onStop() {
        super.onStop()

        mAuth.removeAuthStateListener(mAuthStateListener)
    }
}