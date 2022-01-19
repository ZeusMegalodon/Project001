package com.surelabsid.newmrjempoot.settings.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AkunViewModel: ViewModel() {
    private val _data = MutableLiveData<String>()
    private val _verificationId = MutableLiveData<String>()
    private val _telepon = MutableLiveData<String>()

    var data: LiveData<String> = _data
    var verificationId: LiveData<String> = _verificationId
    var telepon: LiveData<String> = _telepon


    fun getJk(jk: String) {
        _data.postValue(jk)
    }

    fun setVerificationId(verification: String) {
        _verificationId.postValue(verification)
    }

    fun setTelepon(telepon: String) {
        _telepon.postValue(telepon)
    }
}