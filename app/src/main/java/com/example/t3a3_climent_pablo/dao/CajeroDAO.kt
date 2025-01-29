package com.example.t3a3_climent_pablo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.t3a3_climent_pablo.entity.CajeroEntity

@Dao
interface CajeroDAO {

    @Query("SELECT * FROM cajero")
    fun getAllAtm() : MutableList<CajeroEntity>

    @Insert
    fun insertAll(cajeroEntityList : List<CajeroEntity>)

    @Insert
    fun addAtm(cajeroEntity: CajeroEntity)

    @Update
    fun updateAtm(cajeroEntity: CajeroEntity)

    @Delete
    fun deleteAtm(cajeroEntity: CajeroEntity)
}