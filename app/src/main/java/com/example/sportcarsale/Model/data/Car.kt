package com.example.sportcarsale.Model.data

import com.example.sportcarsale.Model.data.*

data class Car(
    var brand: String?,
    var model: String?,
    var productYear: Int?,
    var mileage : Int?,
    var gearType: GearType?,
    var price : Int?,
    var location : String?,
    var engineVolume: Double?,
    var engineType : EngineType?,
    var drive: Drive?,
    var sportType: SportType?,
    var roadType: RoadType?,
    var ownerUID: String?,
    var photoLink : String?,
    var ownerDescription : String?,
    var ownerContact : String?
)