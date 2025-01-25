package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.adapter.CuentasListener
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityGlobalPositionBinding
import com.example.t3a3_climent_pablo.fragments.GlobalPositionFragment
import com.example.t3a3_climent_pablo.fragments.MovementsFragment
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class GlobalPositionActivity : AppCompatActivity(), CuentasListener {

    private lateinit var binding: ActivityGlobalPositionBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        // Obtener el cliente y las cuentas pasadas por el intent
        val bancoOp = MiBancoOperacional.getInstance(this)
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        if (cliente == null) { finish()
            return}
        val cuentasDelCliente = bancoOp?.getCuentas(cliente)
        if (cuentasDelCliente.isNullOrEmpty()) {
            return
        }

        //Logs
        Log.d("GlobalPositionActivity", "Cliente: $cliente")


        val globalPositionFragment = GlobalPositionFragment.newInstance(cliente)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPosicionesGlobales, globalPositionFragment)
            .commit()
    }
    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        if (cuenta != null) {
            var hayCuenta = supportFragmentManager.findFragmentById(R.id.fragmentMovimientos) != null

            val movimientoFragment = MovementsFragment.newInstance(cuenta, -1)

            val bundle = Bundle()
            bundle.putSerializable("cuentaSeleccionada", cuenta)
            movimientoFragment.arguments = bundle

            if (hayCuenta) {
                val movimientoFragment = MovementsFragment.newInstance(cuenta, -1)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentMovimientos, movimientoFragment)
                    .commit()
            } else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentPosicionesGlobales, movimientoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}