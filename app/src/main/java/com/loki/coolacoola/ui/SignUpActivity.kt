package com.loki.coolacoola.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.loki.coolacoola.R
import com.loki.coolacoola.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.sSignupBtn.setOnClickListener {

            if (isFormValid()) {

                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.clickLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun isFormValid(): Boolean {
        val names: String = binding.etNames.text.toString()
        val email: String = binding.etSemail.text.toString()
        val password: String = binding.etSpassword.text.toString()
        val conPassword: String = binding.etConpassword.text.toString()

        if (names.isEmpty()) {
            binding.etNames.error = "enter names"
            return false
        }

        if (email.isEmpty()) {
            binding.etSemail.error = "enter email"
            return false
        }

        if (password.isEmpty()) {
            binding.etSpassword.error = "enter password"
            return false
        }

        if (password.length < 6) {
            binding.etSpassword.error = "should be more than 6 characters"
            return false
        }

        else if (password != conPassword) {
            binding.etConpassword.error = "password does not match"
            return false
        }

        return true
    }

}