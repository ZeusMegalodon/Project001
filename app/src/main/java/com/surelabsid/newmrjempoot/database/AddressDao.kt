package com.surelabsid.newmrjempoot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surelabsid.newmrjempoot.model.SavedAddress
import com.surelabsid.newmrjempoot.response.FavMealItem

@Dao
interface AddressDao {

    @Query("SELECT * FROM tb_address")
    fun getAllAddress(): List<SavedAddress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg savedAddress: SavedAddress?)

    @Query("SELECT * FROM tb_address WHERE id IN (:id)")
    fun getAddressById(id: String): List<SavedAddress>



//    @Query("DELETE FROM tb_favorite WHERE item_id IN (:id)")
//    fun removeFromFav(id: String): Int

}