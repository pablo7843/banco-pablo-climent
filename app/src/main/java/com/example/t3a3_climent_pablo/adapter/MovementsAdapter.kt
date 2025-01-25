package com.example.t3a3_climent_pablo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ItemMovementBinding
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MovementsAdapter(
    var movimientos: List<Movimiento>,
    private val listener: OnMovementClickListener
) : RecyclerView.Adapter<MovementsAdapter.ViewHolder>() {

    interface OnMovementClickListener {
        fun onMovementClick(movimiento: Movimiento)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMovementBinding.bind(view)
        init {
            itemView.setOnClickListener {
                val movimiento = movimientos[adapterPosition]
                listener.onMovementClick(movimiento)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movement, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movimientos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimiento = movimientos[position]
        with(holder.binding) {
            tvDescripcion.text = movimiento.getDescripcion()
            tvImporte.text = movimiento.getImporte().toString()
            tvFecha.text = movimiento.getFechaOperacion().toString()
        }
    }
}