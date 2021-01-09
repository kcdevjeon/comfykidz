package com.kccorp.comfykids

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _whenValue = MutableLiveData("noon")
    private val _whereValue = MutableLiveData("home")
    private val _whatValue = MutableLiveData("sleep")
    private var clickCount = 0;
    val whenValue: LiveData<String> = _whenValue
    val whereValue: LiveData<String> = _whereValue
    val whatValue: LiveData<String> = _whatValue

    fun onClickReset() {
        clickCount = 0
        _whenValue.value = ""
        _whereValue.value = ""
        _whatValue.value = ""
    }

    fun onClickCard() {
        Log.d("COMFYKIDS", "onClickCard")
        clickCount++;
        when (clickCount % 3) {
            0 -> _whatValue.value += "."
            1 -> _whenValue.value += "."
            2 -> _whereValue.value += "."
        }
    }
}