package com.surelabsid.newmrjempoot.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.utils.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _userdata = MutableLiveData<List<UserModel>>()
    val user: LiveData<List<UserModel>> get() = _userdata

    private val _errUser = MutableLiveData<Throwable>()
    val errUser: LiveData<Throwable> get() = _errUser

    init {
        getUser()
    }


    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userdata.postValue(db?.userDao()?.getAllUser())
            } catch (e: Throwable) {
                e.printStackTrace()
                _errUser.postValue(e)
            }

        }
    }
}