package com.example.sportcarsale.Model.carsjsonformatter

import io.reactivex.rxjava3.core.Single

interface CarsJsonFormatter {
    fun getCarBrands() : Single<List<String>>
    fun getModelsByBrand(brand : String) : Single<List<String>>
}