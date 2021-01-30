package com.example.sportcarsale.Service.addcaractivityviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportcarsale.Model.carsbaseapi.CarsBaseAPIImpl
import com.example.sportcarsale.Model.carsjsonformatter.CarsJsonFormatter
import com.example.sportcarsale.Model.carsjsonformatter.CarsJsonFormatterImpl
import com.example.sportcarsale.Model.data.Car
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.io.InputStream

class AddCarsActivityViewModel(private val inputStreamJson : InputStream) : ViewModel() {

    private val carsBaseAPI = CarsBaseAPIImpl()
    private val carsFromJsonFormatter : CarsJsonFormatter = CarsJsonFormatterImpl(inputStreamJson)
    private val carsListMutLiveData = MutableLiveData<List<String>>()
    val carsListLiveData = carsListMutLiveData
    private val modelsMutLiveData = MutableLiveData<List<String>>()
    val modelsLiveData = modelsMutLiveData
    private val auth = FirebaseAuth.getInstance()

    fun addCar(car : Car){
        carsBaseAPI.addCar(car)
    }

    fun getCarBrands(){
        carsFromJsonFormatter.getCarBrands()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                carsListMutLiveData.value = it
            },{
                Throwable(it)
            })
    }

    fun getCarModels(brand : String){
        carsFromJsonFormatter.getModelsByBrand(brand)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                modelsMutLiveData.value = it
            },{
                Throwable(it)
            })
    }

    fun getCurrentUser() = auth.currentUser

}