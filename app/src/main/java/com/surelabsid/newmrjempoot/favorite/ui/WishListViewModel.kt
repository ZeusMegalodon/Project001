package com.surelabsid.newmrjempoot.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.surelabsid.newmrjempoot.database.AppDatabase
import com.surelabsid.newmrjempoot.response.FavMealItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class WishListViewModel : ViewModel() {

    private val _data = MutableLiveData<List<FavMealItem>>()
    val data : LiveData<List<FavMealItem>> get() = _data

    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err

    fun getAllFav(db: AppDatabase){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val res = db.favDao().getAllFav()
                _data.postValue(res)
            }catch (e: Throwable){
                e.printStackTrace()
                _err.postValue(e)
            }
        }
    }
}