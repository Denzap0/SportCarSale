package com.example.sportcarsale.Model.carsbaseapi

import androidx.lifecycle.LiveData
import com.example.sportcarsale.Model.Car
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ValueEventListener

interface CarsBaseAPI {

    fun getAllCars()

    fun getUserCars(user: FirebaseUser)

    fun addCar(car : Car)
}