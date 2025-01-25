package com.example.t3a3_climent_pablo.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.adapter.MovementsAdapter
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.FragmentMovementsBinding
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class MovementsFragment : Fragment() {

    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var binding: FragmentMovementsBinding
    private var movimientos: List<Movimiento> = emptyList()
    private var cuenta: Cuenta? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovementsBinding.inflate(inflater, container, false)
        // Obtener lista de movimientos pasada como argumento
        cuenta = arguments?.getSerializable("cuentaSeleccionada") as? Cuenta
        // Configurar el Bottom Navigation
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_all -> filtrarMovimientos(-1) // Mostrar todos
                R.id.nav_tipo0 -> filtrarMovimientos(0)  // Filtrar tipo 0
                R.id.nav_tipo1 -> filtrarMovimientos(1)  // Filtrar tipo 1
                R.id.nav_tipo2 -> filtrarMovimientos(2)  // Filtrar tipo 2
            }
            true
        }
        val tipo = arguments?.getSerializable("tipo") as? Int ?: -1

        movimientos = cuenta?.let { getMovimientos(it, tipo) } as List<Movimiento>

        movementsAdapter =
            MovementsAdapter(movimientos, object : MovementsAdapter.OnMovementClickListener {
                override fun onMovementClick(movimiento: Movimiento) {
                    val dialogo = layoutInflater.inflate(R.layout.movement_dialog, null)

                    val txtDetalle = dialogo.findViewById<TextView>(R.id.txtDetalle)
                    val formatoFecha = SimpleDateFormat("dd-MM-yyyy")
                    val fecha = formatoFecha.format(movimiento.getFechaOperacion())
                    txtDetalle.text =
                        "id: " + movimiento.getId() + "\ntipo: " + movimiento.getTipo() + "\nfecha operacion: " + fecha + "\ndescripcion: " + movimiento.getDescripcion()

                    context?.let {
                        MaterialAlertDialogBuilder(it)
                            .setView(dialogo)
                            .setCancelable(false)
                            .setPositiveButton(
                                "Aceptar",
                                DialogInterface.OnClickListener { dialog, i ->
                                    dialog.cancel()
                                })
                            .setTitle("Detalles del movimiento")
                            .show()
                    }
                }
            })
        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        binding.recyclerViewMovements.apply {
            layoutManager = linearLayoutManager
            adapter = movementsAdapter
            addItemDecoration(itemDecoration)
        }

        return binding.root
    }

    private fun filtrarMovimientos(tipo: Int) {
        cuenta?.let {
            movimientos = getMovimientos(it, tipo) as List<Movimiento>
            movementsAdapter.updateMovimientos(movimientos)
        }
    }

    fun getMovimientos(cuenta: Cuenta, tipo: Int): ArrayList<*>? {
        val bancoOperacional = MiBancoOperacional.getInstance(context)
        val movimientosCliente: ArrayList<*>?
        if (tipo in 0..2) {
            movimientosCliente = bancoOperacional?.getMovimientosTipo(cuenta, tipo)
        } else {
            movimientosCliente = bancoOperacional?.getMovimientos(cuenta)
        }
        return movimientosCliente
    }

    companion object {
        @JvmStatic
        fun newInstance(c: Cuenta, tipo: Int) =
            MovementsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("cuentaSeleccionada", c)
                    putSerializable("tipo", tipo)
                }
            }
    }

    private fun MovementsAdapter.updateMovimientos(newMovimientos: List<Movimiento>) {
        this.movimientos = newMovimientos
        this.notifyDataSetChanged()
    }
}