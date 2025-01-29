package com.example.t3a3_climent_pablo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ItemAtmBinding
import com.example.t3a3_climent_pablo.entity.CajeroEntity

class AtmEntityAdapter(private var atms: List<CajeroEntity>, private val listener: AtmListListener):
    RecyclerView.Adapter<AtmEntityAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemAtmBinding.bind(view)

        fun setListener(cajeros: CajeroEntity) {
            binding.root.setOnClickListener{
                listener.onClickCajero(cajeros)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_atm, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = atms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cajero = atms.get(position)
        with(holder){
            setListener(cajero)
            val texto = "Cajero ${cajero.id}"
            binding.tvNumeroCajero.text = texto
            binding.tvDireccion.text = cajero.direccion
        }
    }

}