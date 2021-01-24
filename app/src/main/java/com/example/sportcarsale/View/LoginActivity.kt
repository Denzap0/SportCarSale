package com.example.sportcarsale.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.loginactivityviewmodel.LoginActivityViewModelImpl
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var loginActivityViewModel : LoginActivityViewModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signUpButton)
        initViewModel()
        checkUser()
        signInButton.setOnClickListener {signIn()}
        signUpButton.setOnClickListener {signUp()}

    }

    private fun signIn(){
        loginActivityViewModel.signIn(emailEditText.text.toString(),passwordEditText.text.toString())
    }

    private fun signUp(){
        loginActivityViewModel.signUp(emailEditText.text.toString(),passwordEditText.text.toString())
    }

    private fun checkUser(){
        loginActivityViewModel.checkUser()
    }

    private fun initViewModel(){
        loginActivityViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(LoginActivityViewModelImpl::class.java)
        loginActivityViewModel.userLiveData.observe(this, {user -> updateUI(user)})
        loginActivityViewModel.errorLiveData.observe(this, {errorText -> showLoginError(errorText)})
    }

    private fun updateUI(user: FirebaseUser) {
        startActivity(Intent(this,AllCarsActivity::class.java))
    }

    private fun showLoginError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}