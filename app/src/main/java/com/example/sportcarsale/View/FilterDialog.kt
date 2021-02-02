package com.example.sportcarsale.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.EditText
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.sportcarsale.Model.data.carparams.*
import com.example.sportcarsale.Model.data.dialogcarparams.DriveDialog
import com.example.sportcarsale.Model.data.dialogcarparams.EngineTypeDialog
import com.example.sportcarsale.Model.data.dialogcarparams.GearTypeDialog
import com.example.sportcarsale.Model.data.dialogcarparams.RoadTypeDialog
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarViewModelFactory
import com.example.sportcarsale.Service.addcaractivityviewmodel.AddCarsActivityViewModel

class FilterDialog(private val assets: AssetManager) : DialogFragment(),
    AdapterView.OnItemSelectedListener,DialogInterface.OnClickListener {

    private lateinit var brandSpinner: Spinner
    private lateinit var modelSpinner: Spinner
    private lateinit var yearFromEditText: EditText
    private lateinit var yearToEditText: EditText
    private lateinit var priceFromEditText: EditText
    private lateinit var priceToEditText: EditText
    private lateinit var gearTypeSpinner: Spinner
    private lateinit var engineVolumeFromEditText: EditText
    private lateinit var engineVolumeToEditText: EditText
    private lateinit var engineTypeSpinner: Spinner
    private lateinit var driveSpinner: Spinner
    private lateinit var sportTypeSpinner: Spinner
    private lateinit var roadTypeSpinner: Spinner

    private lateinit var filterDialogViewModel: AddCarsActivityViewModel
    private val filterCarMutLiveData = MutableLiveData<FilterCar>()
    val filterCarLiveData : LiveData<FilterCar> = filterCarMutLiveData

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.filter_dialog_layout, null)
        if (view != null) {
            initViews(view)
        }
        initViewModel()
        filterDialogViewModel.getCarBrands()
        return AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton("Save", this)
            .create()
    }

    private fun initViews(view: View) {
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner.onItemSelectedListener = this
        modelSpinner = view.findViewById(R.id.dialogModelSpinner)
        yearFromEditText = view.findViewById(R.id.dialogYearFromEditText)
        yearToEditText = view.findViewById(R.id.dialogYearToEditText)
        priceFromEditText = view.findViewById(R.id.dialogPriceFromEditText)
        priceToEditText = view.findViewById(R.id.dialogPriceToEditText)
        gearTypeSpinner = view.findViewById(R.id.dialogGearTypeSpinner)
        engineVolumeFromEditText = view.findViewById(R.id.dialogEngineVolumeFromEditText)
        engineVolumeToEditText = view.findViewById(R.id.dialogEngineVolumeToEditText)
        engineTypeSpinner = view.findViewById(R.id.dialogEngineTypeSpinner)
        sportTypeSpinner = view.findViewById(R.id.dialogSportTypeSpinner)
        driveSpinner = view.findViewById(R.id.dialogDriveSpinner)
        roadTypeSpinner = view.findViewById(R.id.dialogRoadTypeSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        brandSpinner = view.findViewById(R.id.dialogBrandSpinner)
        setModelSpinnerFirstly()
        setRoadTypeSpinner()
        setSportTypeSpinner()
        setDriveSpinner()
        setEngineTypeSpinner()
        setGearTypeSpinner()
    }

    private fun initViewModel() {
        filterDialogViewModel =
            ViewModelProvider(this, AddCarViewModelFactory(assets.open("cars.json")))
                .get(AddCarsActivityViewModel::class.java)
        filterDialogViewModel.carsListLiveData.observe(
            this,
            { brands -> setBrandsSpinner(brands.toMutableList()) })
        filterDialogViewModel.modelsLiveData.observe(
            this,
            { models -> setModelsSpinner(models.toMutableList()) })
    }

    private fun setModelSpinnerFirstly() {
        modelSpinner.adapter = this.context?.let {
            ArrayAdapter(
                it, R.layout.support_simple_spinner_dropdown_item,
                arrayOf("None")
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            }
        }
    }

    private fun setRoadTypeSpinner() {
        this.context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                RoadTypeDialog.values()
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                roadTypeSpinner.adapter = this
            }
        }
    }

    private fun setSportTypeSpinner() {
        this.context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                RoadTypeDialog.values()
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                sportTypeSpinner.adapter = this
            }
        }
    }

    private fun setDriveSpinner() {
        this.context?.let {
            ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, DriveDialog.values()).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                driveSpinner.adapter = this
            }
        }
    }

    private fun setEngineTypeSpinner() {
        this.context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                EngineTypeDialog.values()
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                engineTypeSpinner.adapter = this
            }
        }
    }

    private fun setGearTypeSpinner() {
        this.context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                GearTypeDialog.values()
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                gearTypeSpinner.adapter = this
            }
        }
    }

    private fun setBrandsSpinner(brands: MutableList<String>) {
        brands.add(0, "None")
        this.context?.let {
            ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, brands).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                brandSpinner.adapter = this
            }
        }
    }

    private fun setModelsSpinner(models: MutableList<String>) {
        models.add(0, "None")
        this.context?.let {
            ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, models).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                modelSpinner.adapter = this
            }
        }
    }

    private fun modelsRequest(brand: String) {
        filterDialogViewModel.getCarModels(brand)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.dialogBrandSpinner -> modelsRequest(parent.getItemAtPosition(position).toString())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun getFilterCar(): FilterCar =
        FilterCar(
            brand = if (brandSpinner.selectedItem.toString() == "None") null else brandSpinner.selectedItem.toString(),
            model = if (modelSpinner.selectedItem.toString() == "None") null else modelSpinner.selectedItem.toString(),
            productYearFrom = if (yearFromEditText.text.toString() == "") null else yearFromEditText.text.toString()
                .toInt(),
            productYearTo = if (yearToEditText.text.toString() == "") null else yearToEditText.text.toString()
                .toInt(),
            gearType = if (gearTypeSpinner.selectedItem.toString() == "None") null else gearTypeSpinner.selectedItem as GearType,
            priceFrom = if (priceFromEditText.text.toString() == "") null else priceFromEditText.text.toString()
                .toInt(),
            priceTo = if (priceToEditText.text.toString() == "") null else priceToEditText.text.toString()
                .toInt(),
            engineVolumeFrom = if (engineVolumeFromEditText.text.toString() == "") null else engineVolumeFromEditText.text.toString()
                .toDouble(),
            engineVolumeTo = if (engineVolumeToEditText.text.toString() == "") null else engineVolumeToEditText.text.toString()
                .toDouble(),
            engineType = if (engineTypeSpinner.selectedItem.toString() == "None") null else engineTypeSpinner.selectedItem as EngineType,
            drive = if (driveSpinner.selectedItem.toString() == "None") null else driveSpinner.selectedItem as Drive,
            sportType = if (sportTypeSpinner.selectedItem.toString() == "None") null else sportTypeSpinner.selectedItem as SportType,
            roadType = if (roadTypeSpinner.selectedItem.toString() == "None") null else roadTypeSpinner.selectedItem as RoadType
        )

    override fun onClick(dialog: DialogInterface?, which: Int) {
        filterCarMutLiveData.value = getFilterCar()
    }


}