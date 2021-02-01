package com.example.sportcarsale.Service.loginfragmentviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragmentViewModelImpl : ViewModel() {
    private val authAPI = FirebaseAuth.getInstance()
    private val userMutLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val userLiveData: LiveData<FirebaseUser> = userMutLiveData
    private val errorMutLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = errorMutLiveData

    fun signIn(email: String, password: String) {
        authAPI.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userMutLiveData.value = authAPI.currentUser
                } else {
                    errorMutLiveData.value = "Sign in error"
                }
            }
    }


    fun signUp(email: String, password: String) {
        authAPI.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userMutLiveData.value = authAPI.currentUser
                } else {
                    Log.d("AAAAAAA", task.exception?.message.toString())
                    errorMutLiveData.value = "Sign up error"
                }
            }
    }

    fun checkUser() {
        if(authAPI.currentUser != null){
            userMutLiveData.value = authAPI.currentUser
        }
    }
}