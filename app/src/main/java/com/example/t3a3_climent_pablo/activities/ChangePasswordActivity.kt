package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityChangePasswordBinding
import com.example.t3a3_climent_pablo.pojo.Cliente

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCambiar.setOnClickListener {

            val cliente = intent.getSerializableExtra("Cliente") as Cliente
            val bancoOperacional = MiBancoOperacional.getInstance(this)


            var contrasenaActual: String = binding.editContrasenaActual.text.toString()
            var contrasenaNueva: String = binding.editContrasenaNueva.text.toString()

            if (cliente.getClaveSeguridad().equals(contrasenaActual)) {

                cliente.setClaveSeguridad(contrasenaNueva)
                bancoOperacional?.changePassword(cliente)

                Toast.makeText(this, "La contraseña s eha cambiado correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnCancelar.setOnClickListener {
            binding.editContrasenaActual.setText("")
            binding.editContrasenaNueva.setText("")
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}