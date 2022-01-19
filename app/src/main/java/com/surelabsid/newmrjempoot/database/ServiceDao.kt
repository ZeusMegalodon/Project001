package com.surelabsid.newmrjempoot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surelabsid.newmrjempoot.response.AllfiturItem


@Dao
interface ServiceDao {

    @Query("SELECT * FROM tb_service")
    fun getAllService(): List<AllfiturItem>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg allfiturItem: AllfiturItem)

    @Query("SELECT * FROM tb_service WHERE service_id IN (:id)")
    fun getService(id: Int): List<AllfiturItem>
}