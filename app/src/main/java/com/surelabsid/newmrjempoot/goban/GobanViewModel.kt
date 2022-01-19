package com.surelabsid.newmrjempoot.goban

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.newmrjempoot.base.BaseViewModel

class GobanViewModel: BaseViewModel() {

    private val _paymentMethod = MutableLiveData<Int>()
    val paymentMethod : LiveData<Int> get() = _paymentMethod
    private val _textPayment = MutableLiveData<String>()
    val textPayment : LiveData<String> get() = _textPayment

    fun setPayment(payment : Int, textPayment: String){
        _paymentMethod.postValue(payment)
        _textPayment.postValue(textPayment)
        Log.d("setPayment", "setPayment: $textPayment")
        Log.d("setPayment", "setPayment: $payment")
    }
}