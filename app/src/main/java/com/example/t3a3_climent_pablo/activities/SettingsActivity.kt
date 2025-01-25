package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}