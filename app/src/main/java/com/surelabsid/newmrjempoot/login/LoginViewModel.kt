package com.surelabsid.newmrjempoot.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.LoginResponseJson
import com.surelabsid.newmrjempoot.response.ResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _res = MutableLiveData<ResponseLogin>()
    val res: LiveData<ResponseLogin> get() = _res

    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err


    fun doLogin(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = Network.getService().login(userModel)
                _res.postValue(data)
            } catch (e: Throwable) {
                _err.postValue(e)
                e.printStackTrace()
            }
        }
    }
}