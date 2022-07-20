package com.loki.coolacoola.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.loki.coolacoola.databinding.FragmentSignUpBinding
import com.loki.coolacoola.util.extension.showToast

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        createAuthListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            sSignupBtn.setOnClickListener {

                if (isFormValid()) {

                    createAccount()

                    //navigate to home
                    navigateToHome()
                }
            }

            clickLogin.setOnClickListener {

                  //navigate to login
                navigateToLogin()
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
                    showToast("Authentication failed")
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
        mAuth = FirebaseAuth.getInstance()

        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val user: FirebaseUser? = it.currentUser

            if (user != null) {

                //navigate to home
                navigateToHome()
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

    private fun navigateToHome() {

        val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun navigateToLogin() {

        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
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