package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.MovementsAdapter
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityMovementsBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MovementsActivity : AppCompatActivity(), MovementsAdapter.OnMovementClickListener {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movimientosAdapter: MovementsAdapter
    private lateinit var cuentasDelCliente: List<Cuenta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        Log.d("MovementsActivity", "Cliente: $cliente")
        val bancoOp = MiBancoOperacional.getInstance(this)

        // Obtener movimientos y cuentas del cliente
        cuentasDelCliente = bancoOp?.getCuentas(cliente) as? List<Cuenta> ?: emptyList()
        Log.d("MovementsActivity", "Cuentas del cliente: $cuentasDelCliente")

        // Listener para actualizar movimientos al seleccionar una cuenta
        binding.spinnerMovimientos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val cuentaSeleccionada = cuentasDelCliente[position]
                updateMovements(cuentaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        if (cuentasDelCliente.isNotEmpty()) {
            updateMovements(cuentasDelCliente[0])
        }

        // Configurar el Spinner con las cuentas
        val cuentasNombres = cuentasDelCliente.map {
            "${it.getBanco()}-${it.getDc()}-${it.getSucursal()}-${it.getNumeroCuenta()}"
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentasNombres)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMovimientos.adapter = spinnerAdapter

        // Configurar RecyclerView
        binding.recViewMovimientos.layoutManager = LinearLayoutManager(this)
        binding.recViewMovimientos.adapter = movimientosAdapter


    }

    private fun updateMovements(cuenta: Cuenta) {
        val bancoOperacional = MiBancoOperacional.getInstance(this)
        val movimientos = bancoOperacional?.getMovimientos(cuenta) ?: emptyList()

        movimientosAdapter = MovementsAdapter(movimientos as List<Movimiento>, this)
        binding.recViewMovimientos.adapter = movimientosAdapter
    }

    override fun onMovementClick(movimiento: Movimiento) {
    }
}
