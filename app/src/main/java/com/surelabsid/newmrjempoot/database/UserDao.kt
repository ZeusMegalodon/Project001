package com.surelabsid.newmrjempoot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surelabsid.newmrjempoot.model.UserModel

@Dao
interface UserDao {
    @Query("SELECT * FROM customer")
    fun getAllUser(): List<UserModel>

    @Query("SELECT * FROM customer WHERE id IN (:id)")
    fun getUser(id: String): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg userModel: UserModel)
}