package com.example.sportcarsale.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportcarsale.Model.Car
import com.example.sportcarsale.R

class AllCarsActivity : AppCompatActivity(), AppActivityInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        startAllCarsFragment()
    }

    private fun startAllCarsFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentLayout, AllCarsFragment(),"AllCarsFragment").commit()
    }

    override fun openCarFragment(car: Car) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, CarFragment(car),"carfragment").addToBackStack("carfragment").commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1){
            supportFragmentManager.popBackStack()
            startAllCarsFragment()
        }else{
            super.onBackPressed()
        }

    }
}