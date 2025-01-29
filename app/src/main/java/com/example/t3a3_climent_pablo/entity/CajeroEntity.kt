package com.example.t3a3_climent_pablo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cajero")
class CajeroEntity (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo var direccion: String?,
    @ColumnInfo var latitud: Double,
    @ColumnInfo var longitud: Double,
    @ColumnInfo val zoom: String?


): Serializable