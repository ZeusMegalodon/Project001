package com.surelabsid.newmrjempoot.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.surelabsid.newmrjempoot.database.AppDatabase
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.response.AllfiturItem
import com.surelabsid.newmrjempoot.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseActivity : AppCompatActivity() {

    var user: UserModel? = null
    lateinit var db: AppDatabase
    var service: AllfiturItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(this, AppDatabase::class.java, Constant.DBNAME).build()

        clearCart()


        CoroutineScope(Dispatchers.IO).launch {
            val listUser = db.userDao().getAllUser()
            if (listUser.isNotEmpty()) {
                user = listUser.firstOrNull()
            }
        }
    }

    private fun clearCart() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
                try{
                    db.cartDao().deleteCart()
                }catch(t: Throwable){
                    t.printStackTrace()
                }
            }
        }
    }

    fun insertService(allfiturItem: AllfiturItem) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    db.serviceDao().insertData(allfiturItem)
                } catch (thr: Throwable) {
                    thr.printStackTrace()
                }
            }
        }
    }

    fun getServiceById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                try {
                    val detailService = db.serviceDao().getService(id)
                    if (detailService.isNotEmpty()) {
                        service = detailService.first()
                    }
                } catch (th: Throwable) {
                    th.printStackTrace()
                }
            }
        }
    }

}