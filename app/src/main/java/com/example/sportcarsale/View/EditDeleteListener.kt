package com.example.sportcarsale.View

import com.example.sportcarsale.Model.data.Car

interface EditDeleteListener {
    fun edit(car : Car)
    fun delete(car : Car)
}