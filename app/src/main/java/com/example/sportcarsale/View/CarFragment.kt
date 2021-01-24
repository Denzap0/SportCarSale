package com.example.sportcarsale.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sportcarsale.Model.Car
import com.example.sportcarsale.R

class CarFragment(private val car : Car) : Fragment() {
    private lateinit var carPhotoImageView : ImageView
    private lateinit var carNameTextView : TextView
    private lateinit var carUsualDescriptionTextView: TextView
    private lateinit var carOwnerDescriptionTextView: TextView
    private lateinit var ownerContactTextView: TextView
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carPhotoImageView = view.findViewById(R.id.carPhotoImageView)
        carNameTextView = view.findViewById(R.id.carName)
        carUsualDescriptionTextView = view.findViewById(R.id.usualDescriptionText)
        carOwnerDescriptionTextView = view.findViewById(R.id.ownerDescriptionText)
        ownerContactTextView = view.findViewById(R.id.contactTextView)
        showCar(view)
    }

    private fun showCar(view : View){
        Glide.with(view.context).load(car.photoLink).into(carPhotoImageView)
        carNameTextView.text = (car.brand + " " + car.model)
        carUsualDescriptionTextView.text = (car.productYear.toString() + ", "
                + car.gearType + ", " + car.engineVolume + ", " + car.engineType + ", " +
                car.mileage + "km, " + car.drive)
        carOwnerDescriptionTextView.text = car.ownerDescription
        ownerContactTextView.text = car.ownerContact

    }
}