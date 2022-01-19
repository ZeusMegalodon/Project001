package com.surelabsid.newmrjempoot.utils

import android.app.Application
import android.content.ContextWrapper
import androidx.room.Room
import com.google.firebase.messaging.FirebaseMessaging
import com.pixplicity.easyprefs.library.Prefs
import com.surelabsid.newmrjempoot.database.AppDatabase

var db: AppDatabase? = null

class Apps : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.Builder()
            .setUseDefaultSharedPreference(true)
            .setContext(baseContext)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .build()



        db = Room.databaseBuilder(baseContext, AppDatabase::class.java, Constant.DBNAME).build()

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Prefs.putString(Constant.TOKEN, it)
        }
    }


}