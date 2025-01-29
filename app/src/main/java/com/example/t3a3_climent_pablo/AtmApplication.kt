package com.example.t3a3_climent_pablo

import android.app.Application
import androidx.room.Room
import com.example.t3a3_climent_pablo.bd.CajeroDB

class AtmApplication : Application() {
    companion object{
        lateinit var database: CajeroDB
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            CajeroDB::class.java,
            "AtmDatabase")
            .build()
    }
}