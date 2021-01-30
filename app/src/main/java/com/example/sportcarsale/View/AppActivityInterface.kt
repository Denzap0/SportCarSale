package com.example.sportcarsale.View

import com.example.sportcarsale.Model.data.Car

interface AppActivityInterface {
    fun openCarFragment(car : Car)

    fun openUserFragment()

    fun showErrorInToast(message : String)
}