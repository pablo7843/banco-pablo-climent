package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ActivityAtmManagementBinding

class AtmManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAtmManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.appbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Banco"


        binding.btnListaCajeros.setOnClickListener{
            startActivity(Intent(this, AtmListActivity::class.java))
        }

        binding.btnAnyadirCajeros.setOnClickListener{
            startActivity(Intent(this, AtmFormActivity::class.java).putExtra("Añadir", true))
        }


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Finaliza la actividad y regresa a la anterior
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }








}