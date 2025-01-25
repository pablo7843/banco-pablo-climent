package com.example.t3a3_climent_pablo.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityLoginBinding
import com.example.t3a3_climent_pablo.pojo.Cliente


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val usuario = binding.etDni.text.toString()
            val contrasena = binding.etPassword.text.toString()

            val bancoOperacional: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

            var cliente = Cliente()
            cliente.setNif(usuario)
            cliente.setClaveSeguridad(contrasena)

            val clienteLogeado = bancoOperacional?.login(cliente) ?: -1

            if (clienteLogeado == -1) {
                Toast.makeText(this, "El cliente o la contraseña no existen", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Cliente", clienteLogeado)
                startActivity(intent)
            }
        }

        binding.buttonSalir.setOnClickListener {
            finish() // cierra la app
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}