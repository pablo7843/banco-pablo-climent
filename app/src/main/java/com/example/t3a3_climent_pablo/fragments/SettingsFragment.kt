package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.t3a3_climent_pablo.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
