package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.t3a3_climent_pablo.AtmApplication
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ActivityMainBinding
import com.example.t3a3_climent_pablo.entity.CajeroEntity
import com.example.t3a3_climent_pablo.fragments.GlobalPositionFragment
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Thread{

            val atmEntityLists : List<CajeroEntity> = listOf(
                CajeroEntity(direccion = "Carrer del Clariano, 1, 46021 Valencia, Valencia, España", latitud = 39.47600769999999, longitud = -0.3524475000000393, zoom = ""),
                CajeroEntity(direccion = "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España", latitud = 39.4710366, longitud = -0.3547525000000178, zoom = ""),
                CajeroEntity(direccion = "Av. del Port, 237, 46011 València, Valencia, España", latitud = 39.46161999999999, longitud = -0.3376299999999901, zoom = ""),
                CajeroEntity(direccion = "Carrer del Batxiller, 6, 46010 València, Valencia, España", latitud = 39.4826729, longitud = -0.3639118999999482, zoom = ""),
                CajeroEntity(direccion = "Av. del Regne de València, 2, 46005 València, Valencia, España", latitud = 39.4647669, longitud = -0.3732760000000326, zoom = "")
            )
            AtmApplication.database.CajeroDAO().insertAll(atmEntityLists)
        }.start()

        // Recibir cliente desde el intent
        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        binding.tvWelcomeMessage?.text = "Bienvenido/a ${cliente.getNombre()}"
        Log.d("MainActivity", "Cliente: $cliente")






        binding.btnPosicionGlobal?.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        // Botón para Movimientos
        binding.btnMovimientos?.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
            Log.d("MainActivity", "Cliente: $cliente")
        }

        // Botón para Transferencias
        binding.btnTransferencias?.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        // Botón para Cambiar Clave
        binding.btnCambiarClave?.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        // Botón para Promociones
        binding.btnPromociones?.setOnClickListener {
            Toast.makeText(this, "Función promociones en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Cajeros
        binding.btnCajeros?.setOnClickListener {
            val intent = Intent(this, AtmManagementActivity::class.java)
            startActivity(intent)
        }


        // Botón para Salir
        binding.btnSalir?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Configurar el Navigation Drawer
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.white, theme)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ajusta los márgenes del DrawerLayout según los insets del sistema (barra de estado, navegación, etc.)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val cliente = intent.getSerializableExtra("Cliente") as Cliente
        when (item.itemId) {
            R.id.nav_home -> {
                //estamos en el home
            }
            R.id.nav_movimientos -> {
                val intent = Intent(this, MovementsActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.nav_transferencias -> {
                val intent = Intent(this, TransferActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.nav_posicion_global -> {
                val intent = Intent(this, GlobalPositionActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.nav_help -> {
                Log.d("MainActivity", "Settings menu clicked")
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_change_password ->{
                Log.d("MainActivity", "Change password menu clicked")
                val intent = Intent(this, ChangePasswordActivity::class.java)
                intent.putExtra("Cliente", cliente)
                startActivity(intent)
            }
            R.id.nav_promociones -> {
                //por hacer
            }
            R.id.nav_cajeros -> {
                val intent = Intent(this, AtmManagementActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                Log.d("MainActivity", "Logout menu clicked")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


}
