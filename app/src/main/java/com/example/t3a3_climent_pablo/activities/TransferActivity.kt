package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityTransferBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene el cliente desde el intent
        val clienteActual = intent.getSerializableExtra("Cliente") as Cliente

        // Configuración de los Spinners
        val spinnerCuentaOrigen: Spinner = findViewById(R.id.sp_source_account)
        val spinnerCuentaDestino: Spinner = findViewById(R.id.sp_target_account)
        val spinnerDivisas: Spinner = findViewById(R.id.sp_currency)

        // Instancia de la base de datos operacional
        val banco = MiBancoOperacional.getInstance(this)
        val listaCuentasCliente = banco?.getCuentas(clienteActual)
        val listaNumerosCuentas = ArrayList<String>()

        // Agrega los números de cuenta del cliente a la lista
        listaCuentasCliente?.forEach { cuenta ->
            if (cuenta is Cuenta) {
                listaNumerosCuentas.add(cuenta.getNumeroCuenta() ?: "")
            }
        }

        // Adaptadores para los Spinners
        val adaptadorCuentas = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaNumerosCuentas)
        val adaptadorDivisas = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.moneda))

        adaptadorCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adaptadorDivisas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCuentaOrigen.adapter = adaptadorCuentas
        spinnerCuentaDestino.adapter = adaptadorCuentas
        spinnerDivisas.adapter = adaptadorDivisas

        // Configuración de los botones de tipo de cuenta
        val radioCuentaPropia: MaterialRadioButton = findViewById(R.id.rb_own_account)
        val radioCuentaAjena: MaterialRadioButton = findViewById(R.id.rb_external_account)
        val inputCuentaAjena: TextInputEditText = findViewById(R.id.et_external_account)

        // Cambia la visibilidad según el tipo de cuenta seleccionado
        radioCuentaPropia.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spinnerCuentaDestino.visibility = View.VISIBLE
                inputCuentaAjena.visibility = View.GONE
            }
        }

        radioCuentaAjena.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spinnerCuentaDestino.visibility = View.GONE
                inputCuentaAjena.visibility = View.VISIBLE
            }
        }

        // Configuración del checkbox para justificantes
        val checkboxJustificante: CheckBox = findViewById(R.id.cb_send_receipt)

        // Acción del botón para enviar transferencia
        binding.btnConfirmTransfer.setOnClickListener {
            val cuentaDestino: String
            val tipoCuentaDestino: String
            val justificante = if (checkboxJustificante.isChecked) "\nEnviar justificante" else ""

            // Obtiene los valores según el tipo de cuenta
            if (radioCuentaPropia.isChecked) {
                cuentaDestino = spinnerCuentaDestino.selectedItem.toString()
                tipoCuentaDestino = "propia"
            } else {
                cuentaDestino = inputCuentaAjena.text.toString()
                tipoCuentaDestino = "ajena"
            }

            // Muestra un mensaje con los datos de la transferencia
            val mensaje = "Cuenta origen:\n${spinnerCuentaOrigen.selectedItem}\nA cuenta $tipoCuentaDestino:\n$cuentaDestino\nImporte: ${binding.etTransferAmount.text}${spinnerDivisas.selectedItem}$justificante"
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }

        // Acción del botón para cancelar transferencia
        binding.btnCancelTransfer.setOnClickListener {
            spinnerCuentaOrigen.setSelection(0)
            spinnerCuentaDestino.setSelection(0)
            inputCuentaAjena.setText("")
            binding.etTransferAmount.setText("")
            spinnerDivisas.setSelection(0)
            checkboxJustificante.isChecked = false
        }
    }
}
