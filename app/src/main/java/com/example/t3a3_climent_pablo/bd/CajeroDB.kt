package com.example.t3a3_climent_pablo.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t3a3_climent_pablo.dao.CajeroDAO
import com.example.t3a3_climent_pablo.entity.CajeroEntity


@Database(entities = [CajeroEntity::class], version = 1)
abstract class CajeroDB : RoomDatabase() {
    abstract fun CajeroDAO(): CajeroDAO
}