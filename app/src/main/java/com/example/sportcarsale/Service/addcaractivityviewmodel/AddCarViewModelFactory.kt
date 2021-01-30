package com.example.sportcarsale.Service.addcaractivityviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.InputStream

class AddCarViewModelFactory(private val jsonInputStream: InputStream) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        AddCarsActivityViewModel(jsonInputStream) as T
}