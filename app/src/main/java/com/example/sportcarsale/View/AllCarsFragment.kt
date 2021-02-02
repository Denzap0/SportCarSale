package com.example.sportcarsale.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.allcarsfragmentviewmodel.AllCarsFragmentViewModelImpl

class AllCarsFragment : Fragment(), AllCarsFragmentInterface {
    private lateinit var allCarsFragmentViewModel: AllCarsFragmentViewModelImpl
    private lateinit var recyclerView: RecyclerView
    private val carsListAdapter =
        CarsListAdapter { car -> appActivityInterface?.openCarFragment(car) }
    private var appActivityInterface: AppActivityInterface? = null
    private lateinit var allCarsList: List<Car>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppActivityInterface) {
            appActivityInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cars_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView(view)
        allCarsFragmentViewModel.fetchCarsList()
    }

    private fun initViewModel() {
        allCarsFragmentViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                AllCarsFragmentViewModelImpl::class.java
            )
        allCarsFragmentViewModel.allCarsLiveData?.observe(viewLifecycleOwner, { carsList -> showCars(carsList) })
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.carsRecyclerView)
        recyclerView.apply {
            adapter = carsListAdapter
            layoutManager =
                LinearLayoutManager(this@AllCarsFragment.context, RecyclerView.VERTICAL, false)
        }
    }

    override fun showCars(cars: List<Car>) {
        carsListAdapter.setCarsList(cars)
    }

}