package com.example.sportcarsale.Service.loginactivityviewmodel

interface LoginActivityViewModel {
    fun signIn(email : String, password : String)
    fun signUp(email : String, password : String)
    fun checkUser()
}