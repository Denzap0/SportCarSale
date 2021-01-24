package com.example.sportcarsale.Service.allcarsfragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sportcarsale.Model.*
import com.example.sportcarsale.Model.carsbaseapi.CarsBaseAPIImpl
import com.google.firebase.auth.FirebaseAuth

class AllCarsFragmentViewModelImpl() : AllCarsFragmentViewModel, ViewModel() {

    private val authAPI = FirebaseAuth.getInstance()
    private var carsBaseAPI: CarsBaseAPIImpl = CarsBaseAPIImpl()
    var allCarsLiveData : LiveData<List<Car>>? = carsBaseAPI.allCarsLiveData

    override fun fetchCarsList() {
        carsBaseAPI.getAllCars()
    }
}