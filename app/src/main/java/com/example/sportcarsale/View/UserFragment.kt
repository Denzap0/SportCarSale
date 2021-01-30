package com.example.sportcarsale.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.example.sportcarsale.Service.userfragmentviewmodel.UserFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser

class UserFragment : Fragment(), View.OnClickListener {

    private lateinit var userActivityListener : AppActivityInterface
    private lateinit var userFragmentViewModel : UserFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private val carsListAdapter = CarsListAdapter { car -> userActivityListener.openCarFragment(car) }
    private lateinit var userEmailTextView : TextView
    private lateinit var addCarButton : FloatingActionButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is AppActivityInterface){
            userActivityListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEmailTextView = view.findViewById(R.id.userEmailTextView)
        addCarButton = view.findViewById(R.id.addCarFloatingActionButton)
        addCarButton.setOnClickListener(this)
        initRecyclerView(view)
        initViewModel()
        userFragmentViewModel.fetchUserCars()
        userFragmentViewModel.getCurrentUser()
    }

    private fun initRecyclerView(view: View){
        recyclerView = view.findViewById(R.id.userCarsRecyclerView)
        recyclerView.apply {
            adapter = carsListAdapter
            layoutManager = LinearLayoutManager(this@UserFragment.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initViewModel(){
        userFragmentViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())
            .get(UserFragmentViewModel::class.java)
        userFragmentViewModel.userCarsLiveData.observe(viewLifecycleOwner,{ carsList -> showCars(carsList)})
        userFragmentViewModel.userLiveData.observe(viewLifecycleOwner,{user -> showUserInfo(user)})
    }

    private fun showCars(carsList : List<Car>){
        carsListAdapter.setCarsList(carsList.toMutableList())
    }

    private fun showUserInfo(user : FirebaseUser){
        userEmailTextView.text = user.email
    }

    override fun onClick(v: View?) {
        if(v?.id == addCarButton.id){
            startActivity(Intent(this@UserFragment.context,AddCarActivity::class.java))
        }
    }
}