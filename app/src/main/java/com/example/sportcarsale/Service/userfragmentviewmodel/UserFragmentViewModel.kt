package com.example.sportcarsale.Service.userfragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportcarsale.Model.carsbaseapi.CarsBaseAPIImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserFragmentViewModel : ViewModel() {

    private val carsBaseAPI = CarsBaseAPIImpl()
    private val auth = FirebaseAuth.getInstance()
    private val userMutLiveData = MutableLiveData<FirebaseUser>()
    val userLiveData : LiveData<FirebaseUser> = userMutLiveData
    val userCarsLiveData = carsBaseAPI.userCarsLiveData


    fun getCurrentUser(){
        userMutLiveData.value = auth.currentUser
    }

    fun fetchUserCars(){
        auth.currentUser?.let { carsBaseAPI.getUserCars(it) }
    }
}