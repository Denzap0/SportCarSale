package com.example.sportcarsale.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.loginfragmentviewmodel.LoginFragmentViewModelImpl
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var loginActivityViewModel : LoginFragmentViewModelImpl
    private lateinit var appActivityInterface: AppActivityInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is AppActivityInterface){
            appActivityInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        signInButton = view.findViewById(R.id.signInButton)
        signUpButton = view.findViewById(R.id.signUpButton)
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
            .get(LoginFragmentViewModelImpl::class.java)
        loginActivityViewModel.userLiveData.observe(this, {user -> updateUI(user)})
        loginActivityViewModel.errorLiveData.observe(this, {errorText -> showLoginError(errorText)})
    }

    private fun updateUI(user: FirebaseUser) {
        appActivityInterface.openUserFragment()
    }

    private fun showLoginError(message: String) {
        appActivityInterface.showErrorInToast(message)
    }
}