package com.example.sportcarsale.Model.carsbaseapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportcarsale.Model.data.*
import com.example.sportcarsale.Model.data.carparams.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class CarsBaseAPIImpl() : CarsBaseAPI {

    private val documentId = "Fb4cP9viwOk4Lnicuyqg"
    private var count by Delegates.notNull<Int>()
    private val db = FirebaseDatabase.getInstance()
    private val carsRef = db.getReference("cars")
    private val allCarsMutLiveData = MutableLiveData<List<Car>>()
    val allCarsLiveData: LiveData<List<Car>> = allCarsMutLiveData
    private val userCarsMutLiveData = MutableLiveData<List<Car>>()
    val userCarsLiveData: LiveData<List<Car>> = userCarsMutLiveData
    private val cloudFireStore = FirebaseFirestore.getInstance()

    init {
        cloudFireStore.collection("maxId")
            .get()
            .addOnCompleteListener { task ->
                task.result?.forEach {
                    count = it.data.getValue("maxid").toString().toInt()
                }
//                count = task.result?.documents?.get(0)?.data?.getValue("maxid").toString().toInt()
            }

    }


    private fun updateAllCars(snapshot: DataSnapshot) {
        val carsList = mutableListOf<Car>()
        snapshot.children.forEach { car ->
            val curCar = Car(
                id = car.key,
                brand = car.child("brand").getValue(String::class.java),
                model = car.child("model").getValue(String::class.java),
                engineType = car.child("engineType").getValue(EngineType::class.java),
                gearType = car.child("gearType").getValue(GearType::class.java),
                mileage = car.child("mileage").getValue(Int::class.java),
                productYear = car.child("productYear").getValue(Int::class.java),
                location = car.child("location").getValue(String::class.java),
                price = car.child("price").getValue(Int::class.java),
                engineVolume = car.child("engineVolume").getValue(Double::class.java),
                drive = car.child("drive").getValue(Drive::class.java),
                photoLink = car.child("photoLink").getValue(String::class.java),
                sportType = car.child("sportType").getValue(SportType::class.java),
                roadType = car.child("roadType").getValue(RoadType::class.java),
                ownerUID = car.child("ownerUID").getValue(String::class.java),
                ownerDescription = car.child("ownerDescription").getValue(String::class.java),
                ownerContact = car.child("ownerContact").getValue(String::class.java)
            )
            carsList.add(curCar)
        }

        allCarsMutLiveData.value = carsList
    }

    private fun updateUserCars(snapshot: DataSnapshot, user: FirebaseUser) {
        val userCarsList = mutableListOf<Car>()
        snapshot.children.forEach { car ->
            if (car.child("ownerUID").getValue(String::class.java) == user.uid) {
                val curCar = Car(
                    id = car.key,
                    brand = car.child("brand").getValue(String::class.java),
                    model = car.child("model").getValue(String::class.java),
                    productYear = car.child("productYear").getValue(Int::class.java),
                    engineType = car.child("engineType").getValue(EngineType::class.java),
                    mileage = car.child("mileage").getValue(Int::class.java),
                    location = car.child("location").getValue(String::class.java),
                    gearType = car.child("gearType").getValue(GearType::class.java),
                    price = car.child("price").getValue(Int::class.java),
                    engineVolume = car.child("engineVolume").getValue(Double::class.java),
                    photoLink = car.child("photoLink").getValue(String::class.java),
                    drive = car.child("drive").getValue(Drive::class.java),
                    sportType = car.child("sportType").getValue(SportType::class.java),
                    roadType = car.child("roadType").getValue(RoadType::class.java),
                    ownerUID = car.child("ownerUID").getValue(String::class.java),
                    ownerDescription = car.child("ownerDescription").getValue(String::class.java),
                    ownerContact = car.child("ownerContact").getValue(String::class.java)
                )
                userCarsList.add(curCar)
            }
        }
        userCarsMutLiveData.value = userCarsList
    }

    override fun getAllCars() {
        carsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                updateAllCars(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun getUserCars(user: FirebaseUser) {
        carsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                updateUserCars(snapshot, user)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    override fun addCar(car: Car) {
        if (car.id == null) {
            count += 1
            car.id = count.toString()
        }
        carsRef.child(car.id!!).setValue(car)
        var update = hashMapOf("maxid" to count)
        cloudFireStore.collection("maxId").document(documentId).set(update)
        count = count.plus(1)

    }

    override fun removeCar(car: Car) {
        car.id?.let { carsRef.child(it).removeValue() }
    }


}