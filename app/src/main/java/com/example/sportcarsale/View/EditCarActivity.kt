package com.example.sportcarsale.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.Model.data.*
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarViewModelFactory
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarsActivityViewModel
import java.util.*

class EditCarActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private val IMAGE_REQUEST_CODE: Int = 10
    private var imgUri: Uri? = null

    private lateinit var brandSpinner: Spinner
    private lateinit var modelSpinner: Spinner
    private lateinit var yearEditText: EditText
    private lateinit var mileageEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var gearTypeSpinner: Spinner
    private lateinit var engineVolumeEditText: EditText
    private lateinit var engineTypeSpinner: Spinner
    private lateinit var driveSpinner: Spinner
    private lateinit var sportTypeSpinner: Spinner
    private lateinit var roadTypeSpinner: Spinner
    private lateinit var locationEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var ownerDescriptionEditText: EditText
    private lateinit var photoImageButton: ImageButton
    private lateinit var addCarButton: Button
    private lateinit var addCarViewModel: AddCarsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_car_activity)
        initViews()
        initViewModel()
        addCarViewModel.getCarBrands()

    }

    private fun initViews() {
        brandSpinner = findViewById(R.id.brandSpinner)
        brandSpinner.onItemSelectedListener = this
        modelSpinner = findViewById(R.id.modelSpinner)
        yearEditText = findViewById(R.id.yearEditText)
        mileageEditText = findViewById(R.id.mileageEditText)
        priceEditText = findViewById(R.id.priceEditText)
        gearTypeSpinner = findViewById(R.id.gearTypeSpinner)
        setGearTypeSpinner()
        engineVolumeEditText = findViewById(R.id.engineVolumeEditText)
        engineTypeSpinner = findViewById(R.id.engineTypeSpinner)
        setEngineTypeSpinner()
        driveSpinner = findViewById(R.id.driveSpinner)
        setDriveSpinner()
        sportTypeSpinner = findViewById(R.id.sportTypeSpinner)
        setSportTypeSpinner()
        roadTypeSpinner = findViewById(R.id.roadTypeSpinner)
        setRoadTypeSpinner()
        locationEditText = findViewById(R.id.locationEditText)
        contactEditText = findViewById(R.id.contactEditText)
        ownerDescriptionEditText = findViewById(R.id.ownerDescriptionEditText)
        photoImageButton = findViewById(R.id.photoImageButton)
        photoImageButton.setOnClickListener(this)
        addCarButton = findViewById(R.id.addCarButton)
        addCarButton.setOnClickListener(this)
    }

    private fun initViewModel() {
        addCarViewModel = ViewModelProvider(this, AddCarViewModelFactory(assets.open("cars.json")))
            .get(AddCarsActivityViewModel::class.java)
        addCarViewModel.carsListLiveData.observe(this, { brands -> setBrandsSpinner(brands) })
        addCarViewModel.modelsLiveData.observe(this, { models -> setModelsSpinner(models) })
    }

    private fun setRoadTypeSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, RoadType.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            roadTypeSpinner.adapter = this
        }

    }

    private fun setSportTypeSpinner() {
        ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            SportType.values()
        ).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            sportTypeSpinner.adapter = this
        }
    }

    private fun setDriveSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Drive.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            driveSpinner.adapter = this
        }
    }

    private fun setEngineTypeSpinner() {
        ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            EngineType.values()
        ).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            engineTypeSpinner.adapter = this
        }
    }

    private fun setGearTypeSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, GearType.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            gearTypeSpinner.adapter = this
        }
    }

    private fun setBrandsSpinner(brands: List<String>) {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, brands).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            brandSpinner.adapter = this
        }
    }

    private fun setModelsSpinner(models: List<String>) {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, models).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            modelSpinner.adapter = this
        }
    }

    private fun modelsRequest(brand: String) {
        addCarViewModel.getCarModels(brand)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.brandSpinner -> modelsRequest(parent.getItemAtPosition(position).toString())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.photoImageButton -> requestImage()
            R.id.addCarButton -> addCar()
        }
    }

    private fun requestImage() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(this, "Select picture"), IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            imgUri = data.data
            if (imgUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imgUri)
                photoImageButton.setImageBitmap(bitmap)
            }

        }
    }

    private fun addCar(){
        if (!checkParamsEnter()){
            Toast.makeText(this,"Not all params entered", Toast.LENGTH_SHORT).show()
        }else{
            val car = Car(null,brandSpinner.selectedItem.toString(),modelSpinner.selectedItem.toString(),
                yearEditText.text.toString().toInt(), mileageEditText.text.toString().toInt(),gearTypeSpinner.selectedItem as GearType,
                priceEditText.text.toString().toInt(),locationEditText.text.toString(),engineVolumeEditText.text.toString().toDouble(),
                engineTypeSpinner.selectedItem as EngineType, driveSpinner.selectedItem as Drive, sportTypeSpinner.selectedItem as SportType,
                roadTypeSpinner.selectedItem as RoadType, addCarViewModel.getCurrentUser()?.uid.toString(),
                UUID.randomUUID().toString(),ownerDescriptionEditText.text.toString(),
                contactEditText.text.toString())
            addCarViewModel.addPicture(car.photoLink!!, imgUri!!)
            addCarViewModel.addCar(car)
            finish()
        }
    }

    private fun checkParamsEnter() : Boolean{
        return (yearEditText.text.isNotEmpty()
                || mileageEditText.text.isNotEmpty()
                || priceEditText.text.isNotEmpty()
                || engineVolumeEditText.text.isNotEmpty()
                || locationEditText.text.isNotEmpty()
                || contactEditText.text.isNotEmpty()
                || ownerDescriptionEditText.text.isNotEmpty()
                || photoImageButton.drawable != null
                || imgUri != null)
    }
}