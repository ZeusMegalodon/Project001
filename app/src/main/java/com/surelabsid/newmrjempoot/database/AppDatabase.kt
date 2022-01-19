package com.surelabsid.newmrjempoot.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.surelabsid.newmrjempoot.model.Cart
import com.surelabsid.newmrjempoot.model.SavedAddress
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.response.FavMealItem
import com.surelabsid.newmrjempoot.response.ItemGopek

@Database(
    entities = [UserModel::class, FavMealItem::class, SavedAddress::class, AllfiturItem::class, Cart::class],
    version = 5,
    exportSchema = false
)
@AutoMigration(from = 4, to = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favDao(): FavDao
    abstract fun addressDao(): AddressDao
    abstract fun serviceDao(): ServiceDao
    abstract fun cartDao(): CartDao
}