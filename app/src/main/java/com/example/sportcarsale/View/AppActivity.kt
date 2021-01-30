package com.example.sportcarsale.View

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sportcarsale.Model.carsjsonformatter.CarsJsonFormatterImpl
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class AppActivity : AppCompatActivity(), AppActivityInterface {

    private lateinit var bottomMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        bottomMenu = findViewById(R.id.navigationView)
        startAllCarsFragment()
        setNavigationViewListener()
    }

    private fun setNavigationViewListener(){
        bottomMenu.setOnNavigationItemReselectedListener { item ->
            when(item.itemId){
                R.id.allCarsItem -> startAllCarsFragment()
                R.id.userCarsItem -> startLoginFragment()
                R.id.settings -> "hi"
            }
        }
    }

    private fun startAllCarsFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, AllCarsFragment(),"AllCarsFragment").commit()
    }

    private fun startLoginFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, LoginFragment(), "LoginFragment").commit()
    }

    override fun openUserFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,UserFragment(),"UserFragment").commit()
    }

    override fun showErrorInToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun openCarFragment(car: Car) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, CarFragment(car),"CarFragment").addToBackStack("carfragment").commit()
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