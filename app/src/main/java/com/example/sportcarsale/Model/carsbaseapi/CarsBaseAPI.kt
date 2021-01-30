package com.example.sportcarsale.Model.carsbaseapi

import com.example.sportcarsale.Model.data.Car
import com.google.firebase.auth.FirebaseUser

interface CarsBaseAPI {

    fun getAllCars()

    fun getUserCars(user: FirebaseUser)

    fun addCar(car : Car)
}