package com.example.t3a3_climent_pablo.activities


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3a3_climent_pablo.AtmApplication
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ActivityAtmFormBinding
import com.example.t3a3_climent_pablo.databinding.DialogDeleteAtmBinding
import com.example.t3a3_climent_pablo.entity.CajeroEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtmFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmFormBinding
    private lateinit var bindingDialog: DialogDeleteAtmBinding
    private lateinit var atm: CajeroEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAtmFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar: androidx.appcompat.widget.Toolbar = binding.appbar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)



        if (intent.hasExtra("Añadir")) {
            binding.btnGuardar.setOnClickListener{
                if (binding.etDireccion.hint?.toString().isNullOrEmpty() &&
                    binding.etLatitud.hint?.toString().isNullOrEmpty() &&
                    binding.etLongitud.hint?.toString().isNullOrEmpty()) {
                    if (binding.etDireccion.text.toString().isEmpty() ||
                        binding.etLatitud.text.toString().isEmpty() ||
                        binding.etLongitud.text.toString().isEmpty()) {
                        Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()
                    } else {
                        addAtm()
                    }
                }
            }
        } else {
            atm = intent.getSerializableExtra("Atm") as CajeroEntity
            binding.etDireccion.hint = atm.direccion
            binding.etLatitud.hint = atm.latitud.toString()
            binding.etLongitud.hint = atm.longitud.toString()

            binding.btnGuardar.setOnClickListener {



                updateAtm(atm)
            }
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }







        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateAtm(atm: CajeroEntity){

        if (binding.etDireccion.text.isNullOrEmpty()){
            atm.direccion = binding.etDireccion.hint.toString()
        } else {
            atm.direccion = binding.etDireccion.text.toString()
        }

        if (binding.etLatitud.text.isNullOrEmpty()) {
            atm.latitud = binding.etLatitud.hint.toString().toDouble()
        } else {
            atm.latitud = binding.etLatitud.text.toString().toDouble()
        }

        if (binding.etLongitud.text.isNullOrEmpty()) {
            atm.longitud = binding.etLongitud.hint.toString().toDouble()
        } else {
            atm.longitud = binding.etLongitud.text.toString().toDouble()
        }

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                AtmApplication.database.CajeroDAO().updateAtm(atm)
            }
        }
        startActivity(Intent(this, AtmManagementActivity::class.java))
        finish()

    }
    private fun addAtm(){
        val newAtm = CajeroEntity(
            direccion = binding.etDireccion.text.toString(),
            latitud = binding.etLatitud.text.toString().toDouble(),
            longitud = binding.etLongitud.text.toString().toDouble(),
            zoom = ""
        )

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                AtmApplication.database.CajeroDAO().addAtm(newAtm)
            }
        }
        startActivity(Intent(this, AtmManagementActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gestion_atm_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_borrar -> {
                bindingDialog = DialogDeleteAtmBinding.inflate(layoutInflater)
                MaterialAlertDialogBuilder(this)
                    .setView(bindingDialog.root)
                    .setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss()}
                    .setPositiveButton("OK") { dialog, _ ->
                        deleteAtm(atm)
                        dialog.dismiss()
                    }
                    .create().show()
                true
            }
            R.id.nav_actualizar -> {
                Toast.makeText(this, "Actualizar", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAtm(atm: CajeroEntity) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                AtmApplication.database.CajeroDAO().deleteAtm(atm)
            }
        }
        startActivity(Intent(this, AtmManagementActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        finish()

    }
}