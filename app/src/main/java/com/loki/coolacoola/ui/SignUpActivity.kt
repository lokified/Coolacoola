package com.loki.coolacoola.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.loki.coolacoola.R
import com.loki.coolacoola.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener;

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        createAuthListener()



        binding.sSignupBtn.setOnClickListener {

            if (isFormValid()) {

                createAccount()

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

    private fun createAccount() {
        val email: String = binding.etSemail.text.toString()
        val password: String = binding.etSpassword.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    createFirebaseUserProfile(task.result.user)
                }
                else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun createFirebaseUserProfile(user : FirebaseUser?) {

        val addName = UserProfileChangeRequest.Builder()
            .setDisplayName(binding.etNames.text.toString())
            .build()

        user?.updateProfile(addName)
    }

    private fun createAuthListener() {

        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val user: FirebaseUser? = it.currentUser

            if (user != null) {
                Intent(this, MainActivity::class.java).also { intent ->

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
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

    override fun onStart() {
        super.onStart()

        mAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onStop() {
        super.onStop()

        mAuth.removeAuthStateListener(mAuthStateListener)
    }

}