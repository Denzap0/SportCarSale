package com.example.sportcarsale.View

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.Model.data.*
import com.example.sportcarsale.Model.data.carparams.*
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarViewModelFactory
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarsActivityViewModel
import java.util.*

class EditCarActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private val IMAGE_REQUEST_CODE: Int = 10
    private var imgUri: Uri? = null
    private var isPhotoLinkChanged = false

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
    private lateinit var editCarButton: Button
    private lateinit var addCarViewModel: AddCarsActivityViewModel
    private lateinit var car : Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_car_activity)
        car = intent.getSerializableExtra("car") as Car
        initViewModel()
        initViews()
        addCarViewModel.getCarBrands()

    }

    private fun initViews() {
        brandSpinner = findViewById(R.id.brandSpinner)
        brandSpinner.onItemSelectedListener = this
        modelSpinner = findViewById(R.id.modelSpinner)
        yearEditText = findViewById(R.id.yearEditText)
        setYearEditText()
        mileageEditText = findViewById(R.id.mileageEditText)
        setMileageEditText()
        priceEditText = findViewById(R.id.priceEditText)
        setPriceEditText()
        gearTypeSpinner = findViewById(R.id.gearTypeSpinner)
        setGearTypeSpinner()
        engineVolumeEditText = findViewById(R.id.engineVolumeEditText)
        setEngineVolumeEditText()
        engineTypeSpinner = findViewById(R.id.engineTypeSpinner)
        setEngineTypeSpinner()
        driveSpinner = findViewById(R.id.driveSpinner)
        setDriveSpinner()
        sportTypeSpinner = findViewById(R.id.sportTypeSpinner)
        setSportTypeSpinner()
        roadTypeSpinner = findViewById(R.id.roadTypeSpinner)
        setRoadTypeSpinner()
        locationEditText = findViewById(R.id.locationEditText)
        setLocationEditText()
        contactEditText = findViewById(R.id.contactEditText)
        setContactEditText()
        ownerDescriptionEditText = findViewById(R.id.ownerDescriptionEditText)
        setOwnerDescriptionEditText()
        photoImageButton = findViewById(R.id.photoImageButton)
        photoImageButton.setOnClickListener(this)
        setPhotoImageView()
        editCarButton = findViewById(R.id.addCarButton)
        editCarButton.setOnClickListener(this)
        editCarButton.text = "Edit"

    }

    private fun setPhotoImageView(){
        car.photoLink?.let { addCarViewModel.getPicture(it) }
    }

    private fun setOwnerDescriptionEditText(){
        ownerDescriptionEditText.setText(car.ownerDescription.toString())
    }

    private fun setContactEditText(){
        contactEditText.setText(car.ownerContact.toString())
    }

    private fun setLocationEditText(){
        locationEditText.setText(car.location.toString())
    }

    private fun setEngineVolumeEditText(){
        engineVolumeEditText.setText(car.engineVolume.toString())
    }
    private fun setPriceEditText(){
        priceEditText.setText(car.price.toString())
    }

    private fun setMileageEditText(){
        mileageEditText.setText(car.mileage.toString())
    }

    private fun setYearEditText(){
        yearEditText.setText(car.productYear.toString())
    }

    private fun initViewModel() {
        addCarViewModel = ViewModelProvider(this, AddCarViewModelFactory(assets.open("cars.json")))
            .get(AddCarsActivityViewModel::class.java)
        addCarViewModel.carsListLiveData.observe(this, { brands -> setBrandsSpinner(brands) })
        addCarViewModel.modelsLiveData.observe(this, { models -> setModelsSpinner(models) })
        addCarViewModel.bitmapLiveData.observe(this,{bitmap -> setPhotoBitmap(bitmap)})
    }

    private fun setRoadTypeSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, RoadType.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            roadTypeSpinner.adapter = this
            roadTypeSpinner.setSelection(getPosition(car.roadType))
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
            sportTypeSpinner.setSelection(getPosition(car.sportType))
        }
    }

    private fun setDriveSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Drive.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            driveSpinner.adapter = this
            driveSpinner.setSelection(getPosition(car.drive))
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
            engineTypeSpinner.setSelection(getPosition(car.engineType))
        }
    }

    private fun setGearTypeSpinner() {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, GearType.values()).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            gearTypeSpinner.adapter = this
            gearTypeSpinner.setSelection(getPosition(car.gearType))
        }
    }

    private fun setBrandsSpinner(brands: List<String>) {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, brands).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            brandSpinner.adapter = this
            brandSpinner.setSelection(getPosition(car.brand))
        }
    }

    private fun setModelsSpinner(models: List<String>) {
        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, models).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            modelSpinner.adapter = this
            brandSpinner.setSelection(getPosition(car.model))
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
        isPhotoLinkChanged = true
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
            val car = Car(
                car.id,brandSpinner.selectedItem.toString(),modelSpinner.selectedItem.toString(),
                yearEditText.text.toString().toInt(), mileageEditText.text.toString().toInt(),gearTypeSpinner.selectedItem as GearType,
                priceEditText.text.toString().toInt(),locationEditText.text.toString(),engineVolumeEditText.text.toString().toDouble(),
                engineTypeSpinner.selectedItem as EngineType, driveSpinner.selectedItem as Drive, sportTypeSpinner.selectedItem as SportType,
                roadTypeSpinner.selectedItem as RoadType, addCarViewModel.getCurrentUser()?.uid.toString(),
                if(imgUri == null) car.photoLink else UUID.randomUUID().toString(),ownerDescriptionEditText.text.toString(),
                contactEditText.text.toString())
            if(imgUri != null) {
                addCarViewModel.addPicture(car.photoLink!!, imgUri!!)
            }
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

    private fun setPhotoBitmap(bitmap: Bitmap){
        photoImageButton.setImageBitmap(bitmap)
    }
}