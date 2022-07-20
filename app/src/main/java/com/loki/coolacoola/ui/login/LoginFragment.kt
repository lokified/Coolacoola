package com.loki.coolacoola.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.loki.coolacoola.databinding.FragmentLoginBinding
import com.loki.coolacoola.util.extension.showToast

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setUpListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            signupBtn.setOnClickListener {
                //navigate to sign up
                navigateToSignUp()
            }

            signinBtn.setOnClickListener {
                login()
            }
        }
    }

    private fun setUpListeners() {

        mAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val user : FirebaseUser? = it.currentUser

            if (user != null) {

                //navigate to home
                navigateToHome()
            }
        }
    }


    private fun login() {

        binding.apply {

            loginProgress.visibility = View.VISIBLE

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()


            if (email.isEmpty()) {
                lEmail.error = "Enter Email"
                return
            }

            if (password.isEmpty()) {
                lPassword.error = "Enter password"
                return
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {

                        loginProgress.visibility = View.GONE
                        showToast("Authentication failed")
                    }
                    else{

                        //navigate to home
                        loginProgress.visibility = View.GONE
                        navigateToHome()
                    }
                }
        }

    }

    private fun navigateToHome() {

        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSignUp() {

        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(action)
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