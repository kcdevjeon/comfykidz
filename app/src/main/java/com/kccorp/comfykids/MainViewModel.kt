package com.kccorp.comfykids

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mainText: ObservableField<String> = ObservableField("Main")
    var whenValue: String = "noon"
    var whereValue: String = "home"
    var whatValue: String = "sleep"

    fun onClickButton() {
        whenValue += "."
        whereValue += "."
        whatValue += "."
    }

}