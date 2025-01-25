package com.example.t3a3_climent_pablo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ItemCuentaBinding
import com.example.t3a3_climent_pablo.pojo.Cuenta

class GlobalPositionAdapter(private val cuentas: List<Cuenta>, private val listener: CuentasListener): RecyclerView.Adapter<GlobalPositionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCuentaBinding.bind(view)
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cuenta, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuenta = cuentas.get(position)
        with(holder) {
            binding.textNumeroCuenta.text = cuenta.getBanco() + "-" + cuenta.getSucursal() + "-" + cuenta.getDc() + "-" + cuenta.getNumeroCuenta()
            binding.textSaldo.text = cuenta.getSaldoActual().toString()
            binding.textSaldo.setTextColor(
                if (cuenta.getSaldoActual()!! > 0) {
                    ContextCompat.getColor(context, R.color.black)
                } else {
                    ContextCompat.getColor(context, R.color.rojo)
                }
            )

            binding.root.setOnClickListener {
                listener.onCuentaSeleccionada(cuenta)
            }
        }
    }

    override fun getItemCount(): Int = cuentas.size

}