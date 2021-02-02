package com.example.sportcarsale.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.google.android.material.bottomnavigation.BottomNavigationView
val filterCarMutLiveData = MutableLiveData<FilterCar>()
class AppActivity : AppCompatActivity(), AppActivityInterface {

    private lateinit var bottomMenu: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        bottomMenu = findViewById(R.id.navigationView)
        startAllCarsFragment()
        setNavigationViewListener()
    }

    private fun setNavigationViewListener() {
        bottomMenu.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.allCarsItem -> startAllCarsFragment()
                R.id.userCarsItem -> startLoginFragment()
                R.id.settings -> openSettingsFragment()
            }
        }
    }

    private fun startAllCarsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, AllCarsFragment(), "AllCarsFragment").commit()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.sort_icon, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.filterList -> openFilterDialog()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun startLoginFragment() {
        val myFragment = LoginFragment()
        val checkUser =
            this.getSharedPreferences("checkUser", MODE_PRIVATE).getBoolean("checkUser", false)
        val autoLogin = this.getSharedPreferences("autoLogin", MODE_PRIVATE).getBoolean("autoLogin", true)
        val bundle = Bundle().apply {
            putBoolean("checkUser", checkUser && autoLogin)
        }
        myFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, myFragment, "LoginFragment").commit()
    }

    override fun openUserFragment() {
        this.getSharedPreferences("checkUser", MODE_PRIVATE).edit().putBoolean("checkUser", true)
            .apply()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, UserFragment(), "UserFragment").commit()
    }

    override fun openLoginFragment() {
        this.getSharedPreferences("checkUser", MODE_PRIVATE).edit().putBoolean("checkUser", false)
            .apply()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, LoginFragment(), "LoginFragment").commit()
    }

    override fun openSettingsFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,SettingsFragment(),"SettingsFragment").commit()
    }

    override fun showErrorInToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun openCarFragment(car: Car) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, CarFragment(car), "CarFragment")
            .addToBackStack("carfragment").commit()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
            startAllCarsFragment()
        } else {
            super.onBackPressed()
        }

    }

//    private fun openFilterDialog() {
//        val filterDialog = FilterDialog(assets)
//        filterDialog.show(supportFragmentManager, "Filter")
//        filterDialog.filterCarLiveData.observe(this, {car -> filterCarMutLiveData.value = car})
//    }
}