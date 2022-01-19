package com.surelabsid.newmrjempoot.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.GeneralResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {


    private val _res = MutableLiveData<GeneralResponse>()
    val res : LiveData<GeneralResponse> get() = _res

    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err


    fun doRegister(userModel: UserModel){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val api = Network.getService().doRegister(userModel)
                _res.postValue(api)
            }catch (t: Throwable){
                t.printStackTrace()
                _err.postValue(t)
            }
        }
    }
}