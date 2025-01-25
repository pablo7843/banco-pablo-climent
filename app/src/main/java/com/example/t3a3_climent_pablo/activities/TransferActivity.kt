package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.fragments.TransferFragment
import com.example.t3a3_climent_pablo.pojo.Cliente

class TransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        // Si no es un cambio de configuración, agregamos el fragment
        if (savedInstanceState == null) {
            val fragment = TransferFragment()

            // Obtén el cliente pasado como extra y añádelo al fragment
            val cliente = intent.getSerializableExtra("Cliente") as? Cliente
            Log.d("TransferActivity", "Cliente recibido: $cliente")
            val bundle = Bundle()
            bundle.putSerializable("Cliente", cliente)
            fragment.arguments = bundle

            // Usamos FragmentTransaction para agregar el fragmento
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment) // 'fragment_container' es el FrameLayout en activity_transferencias.xml
                .commit()
        }
    }
}


