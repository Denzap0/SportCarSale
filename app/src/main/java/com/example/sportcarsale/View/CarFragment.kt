package com.example.sportcarsale.View

import android.content.ClipDescription
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.carfragmentviewmodel.CarFragmentViewModel

class CarFragment(private val car : Car) : Fragment() {
    private lateinit var carPhotoImageView : ImageView
    private lateinit var carNameTextView : TextView
    private lateinit var carUsualDescriptionTextView: TextView
    private lateinit var carOwnerDescriptionTextView: TextView
    private lateinit var ownerContactTextView: TextView
    private lateinit var sportDescriptionTextView: TextView
    private lateinit var locationTextView : TextView
    private lateinit var priceTextView: TextView
    private lateinit var viewModel : CarFragmentViewModel

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
        sportDescriptionTextView = view.findViewById(R.id.sportDescriptionTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        priceTextView = view.findViewById(R.id.priceTextView)
        initViewModel()
        showCar(view)
    }

    private fun showCar(view : View){
        car.photoLink?.let { viewModel.getImageBitmap(it) }
        carNameTextView.text = (car.brand + " " + car.model)
        carUsualDescriptionTextView.text = (car.productYear.toString() + ", "
                + car.gearType + ", " + car.engineVolume + ", " + car.engineType + ", " +
                car.mileage + "km, " + car.drive)
        carOwnerDescriptionTextView.text = car.ownerDescription
        ownerContactTextView.text = car.ownerContact
        sportDescriptionTextView.text = (car.sportType.toString() + " " + car.roadType)
        locationTextView.text = car.location
        priceTextView.text = (car.price.toString() + "$")
    }

    private fun setBitmap(bitmap : Bitmap){
        carPhotoImageView.setImageBitmap(bitmap)
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CarFragmentViewModel::class.java)
        viewModel.bitmapLiveData.observe(this,{bitmap -> setBitmap(bitmap)})
    }
}