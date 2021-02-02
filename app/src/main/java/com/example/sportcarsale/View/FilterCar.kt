package com.example.sportcarsale.View

import com.example.sportcarsale.Model.data.carparams.*

class FilterCar(
    var brand: String?,
    var model: String?,
    var productYearFrom: Int?,
    var productYearTo: Int?,
    var gearType: GearType?,
    var priceFrom: Int?,
    var priceTo: Int?,
    var engineVolumeFrom: Double?,
    var engineVolumeTo: Double?,
    var engineType: EngineType?,
    var drive: Drive?,
    var sportType: SportType?,
    var roadType: RoadType?
)