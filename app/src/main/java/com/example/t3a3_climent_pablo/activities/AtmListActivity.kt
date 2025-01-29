package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.AtmApplication
import com.example.t3a3_climent_pablo.entity.CajeroEntity
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.adapter.AtmEntityAdapter
import com.example.t3a3_climent_pablo.adapter.AtmListListener
import com.example.t3a3_climent_pablo.databinding.ActivityAtmListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtmListActivity : AppCompatActivity(), AtmListListener {

    private lateinit var cajeroAdapter: AtmEntityAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityAtmListBinding

    private lateinit var atmEntityList: List<CajeroEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAtmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.appbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Banco"

        GlobalScope.launch(Dispatchers.Main) {
            val atmEntityList = withContext(Dispatchers.IO) {
                AtmApplication.database.CajeroDAO().getAllAtm()
            }
            cajeroAdapter = AtmEntityAdapter(atmEntityList, this@AtmListActivity)
            linearLayoutManager = LinearLayoutManager(this@AtmListActivity)

            binding.rvCajeros.apply {
                layoutManager = linearLayoutManager
                adapter = cajeroAdapter
            }
        }






        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClickCajero(cajero: CajeroEntity) {
        startActivity(Intent(this, AtmFormActivity::class.java).putExtra("Atm", cajero))
    }
}