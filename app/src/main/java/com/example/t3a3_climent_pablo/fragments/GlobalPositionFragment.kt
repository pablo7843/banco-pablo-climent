package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.CuentasListener
import com.example.t3a3_climent_pablo.adapter.GlobalPositionAdapter
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.FragmentGlobalPositionBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta

class GlobalPositionFragment : Fragment() {

    private lateinit var globalPositionAdapter: GlobalPositionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration
    private lateinit var binding: FragmentGlobalPositionBinding
    private var cuentas: List<Cuenta> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlobalPositionBinding.inflate(inflater, container, false)

        val cliente = arguments?.getSerializable("ClienteSeleccionado") as? Cliente
        cliente?.let {
            cuentas = getCuentas(cliente)
        }

        val activity = activity as? CuentasListener
        globalPositionAdapter = GlobalPositionAdapter(cuentas, activity ?: object : CuentasListener {
            override fun onCuentaSeleccionada(cuenta: Cuenta) { }
        })

        linearLayoutManager = LinearLayoutManager(context)
        itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        binding.recyclerViewPosGlobal.apply {
            layoutManager = linearLayoutManager
            adapter = globalPositionAdapter
            addItemDecoration(itemDecoration)
        }

        return binding.root
    }

    private fun getCuentas(cliente: Cliente): List<Cuenta> {
        val bancoOperacional = MiBancoOperacional.getInstance(context)
        return bancoOperacional?.getCuentas(cliente) as List<Cuenta>
    }

    companion object {
        @JvmStatic
        fun newInstance(cliente: Cliente) =
            GlobalPositionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ClienteSeleccionado", cliente)
                }
            }
    }
}