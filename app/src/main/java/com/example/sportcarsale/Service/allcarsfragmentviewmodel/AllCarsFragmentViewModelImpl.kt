package com.example.sportcarsale.Service.allcarsfragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sportcarsale.Model.carsbaseapi.CarsBaseAPIImpl
import com.example.sportcarsale.Model.data.Car
import com.google.firebase.auth.FirebaseAuth

class AllCarsFragmentViewModelImpl() : AllCarsFragmentViewModel, ViewModel() {

    private var carsBaseAPI: CarsBaseAPIImpl = CarsBaseAPIImpl()
    var allCarsLiveData : LiveData<List<Car>>? = carsBaseAPI.allCarsLiveData

    override fun fetchCarsList() {
        carsBaseAPI.getAllCars()
    }
}