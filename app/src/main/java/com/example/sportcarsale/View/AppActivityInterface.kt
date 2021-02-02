package com.example.sportcarsale.View

import com.example.sportcarsale.Model.data.Car

interface AppActivityInterface {
    fun openCarFragment(car : Car)

    fun openUserFragment()

    fun openLoginFragment()

    fun openSettingsFragment()

    fun showErrorInToast(message : String)
}