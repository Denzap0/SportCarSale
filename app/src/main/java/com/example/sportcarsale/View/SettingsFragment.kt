package com.example.sportcarsale.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.sportcarsale.R

class SettingsFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private lateinit var autoLoginSwitchCompat: SwitchCompat
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoLoginSwitchCompat = view.findViewById(R.id.autoLoginSwitchCompat)
        if (this.context != null) {
            autoLoginSwitchCompat.isChecked =
                this.context?.getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
                    ?.getBoolean("autoLogin", false)!!
        }
        autoLoginSwitchCompat.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            this.context?.getSharedPreferences("autoLogin", Context.MODE_PRIVATE)?.edit()
                ?.putBoolean("autoLogin", true)?.apply()
        } else {
            this.context?.getSharedPreferences("autoLogin", Context.MODE_PRIVATE)?.edit()
                ?.putBoolean("autoLogin", false)?.apply()
        }

    }
}