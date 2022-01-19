package com.surelabsid.newmrjempoot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surelabsid.newmrjempoot.response.FavMealItem

@Dao
interface FavDao {

    @Query("SELECT * FROM tb_favorite")
    fun getAllFav(): List<FavMealItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg favMealItem: FavMealItem)

//    @Query("DELETE FROM tb_favorite WHERE item_id IN (:id)")
//    fun removeFromFav(id: String): Int

}