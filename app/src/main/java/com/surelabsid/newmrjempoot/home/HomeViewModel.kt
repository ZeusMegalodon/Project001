package com.surelabsid.newmrjempoot.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.surelabsid.newmrjempoot.base.BaseViewModel
import com.surelabsid.newmrjempoot.model.JsonRequest
import com.surelabsid.newmrjempoot.model.UserModel
import com.surelabsid.newmrjempoot.network.Network
import com.surelabsid.newmrjempoot.response.ResponseHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val _res = MutableLiveData<ResponseHome>()
    val res: LiveData<ResponseHome> get() = _res


    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err


    fun getHomeData(jsonRequest: JsonRequest, user: UserModel?) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val api = Network.getRetrofitSurelabs(user?.phone_number, user?.password).getHomeData(jsonRequest)
                _res.postValue(api)
            } catch (e: Throwable) {
                e.printStackTrace()

            }
        }
    }
}