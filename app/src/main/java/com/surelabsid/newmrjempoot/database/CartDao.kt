package com.surelabsid.newmrjempoot.database

import androidx.room.*
import com.surelabsid.newmrjempoot.model.Cart
import com.surelabsid.newmrjempoot.model.SavedAddress
import com.surelabsid.newmrjempoot.response.FavMealItem
import com.surelabsid.newmrjempoot.response.ItemGopek

@Dao
interface CartDao {

    @Query("SELECT * FROM item_gopek")
    fun getAllCart(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(vararg itemGopek: Cart?)

    @Query("SELECT * FROM item_gopek WHERE item_id IN (:id)")
    fun getCartById(id: String): List<Cart>


    @Query("DELETE FROM item_gopek")
    fun deleteCart()

}