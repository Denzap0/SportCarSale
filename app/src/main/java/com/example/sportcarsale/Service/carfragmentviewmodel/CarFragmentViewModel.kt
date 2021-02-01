package com.example.sportcarsale.Service.carfragmentviewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportcarsale.Model.data.Car
import com.google.firebase.storage.FirebaseStorage

class CarFragmentViewModel : ViewModel() {

    private val storageRef = FirebaseStorage.getInstance().reference
    private val bitmapMutLiveData = MutableLiveData<Bitmap>()
    val bitmapLiveData = bitmapMutLiveData

    fun getImageBitmap(photoUUID : String){
        storageRef.child(photoUUID).getBytes(1024*1024).addOnSuccessListener {
            bitmapMutLiveData.value = BitmapFactory.decodeByteArray(it,0,it.size)
        }
    }
}