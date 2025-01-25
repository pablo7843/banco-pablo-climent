// TransferFragment.kt
package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.t3a3_climent_pablo.databinding.FragmentTransferBinding
import com.example.t3a3_climent_pablo.pojo.Cliente

class TransferFragment : Fragment() {

    private lateinit var binding: FragmentTransferBinding
    private lateinit var cliente: Cliente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragmento
        binding = FragmentTransferBinding.inflate(inflater, container, false)

        // Recuperamos el cliente pasado al fragmento
        cliente = arguments?.getSerializable("Cliente") as Cliente
        Log.d("TransferFragment", "Cliente recibido: $cliente")

        val cuentas = cliente.obtenerListaCuentas()

        // Configurar adaptadores para los Spinners
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            cuentas?.map { it.getNumeroCuenta() } ?: emptyList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCuentaOrigen.adapter = adapter
        binding.spinnerCuentaDestino.adapter = adapter

        // RadioButton para seleccionar cuenta propia o ajena
        binding.radioButtonCuentaPropia.setOnClickListener {
            binding.spinnerCuentaDestino.visibility = View.VISIBLE
            binding.editTextCuentaAjena.visibility = View.GONE
        }

        binding.radioButtonCuentaAjena.setOnClickListener {
            binding.spinnerCuentaDestino.visibility = View.GONE
            binding.editTextCuentaAjena.visibility = View.VISIBLE
        }

        // Configurar el botón "Enviar"
        binding.buttonEnviar.setOnClickListener {
            val cuentaOrigen = binding.spinnerCuentaOrigen.selectedItem.toString()
            val cuentaDestino = if (binding.radioButtonCuentaPropia.isChecked) {
                binding.spinnerCuentaDestino.selectedItem.toString()
            } else {
                binding.editTextCuentaAjena.text.toString()
            }
            val cantidad = binding.editTextCantidad.text.toString()
            val moneda = binding.spinnerMoneda.selectedItem.toString()
            val enviarJustificante = binding.checkBoxJustificante.isChecked

            val mensaje = """
                Cuenta origen: $cuentaOrigen
                Cuenta destino: $cuentaDestino
                Importe: $cantidad $moneda
                Enviar justificante: $enviarJustificante
            """.trimIndent()

            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }

        // Configurar el botón "Cancelar"
        binding.buttonCancelar.setOnClickListener {
            binding.editTextCuentaAjena.text.clear()
            binding.editTextCantidad.text.clear()
            binding.checkBoxJustificante.isChecked = false
            binding.spinnerCuentaOrigen.setSelection(0)
            binding.spinnerCuentaDestino.setSelection(0)
            binding.spinnerMoneda.setSelection(0)
            binding.radioButtonCuentaPropia.isChecked = true
            binding.spinnerCuentaDestino.visibility = View.VISIBLE
            binding.editTextCuentaAjena.visibility = View.GONE
        }

        return binding.root
    }
}


