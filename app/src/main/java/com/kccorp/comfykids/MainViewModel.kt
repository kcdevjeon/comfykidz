package com.kccorp.comfykids

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _whenValue = MutableLiveData("")
    val whenValue: LiveData<String> = _whenValue
    private val _whereValue = MutableLiveData("")
    val whereValue: LiveData<String> = _whereValue
    private val _whatValue = MutableLiveData("")
    val whatValue: LiveData<String> = _whatValue
    private val _muteState = MutableLiveData(false)
    val muteState: LiveData<Boolean> = _muteState

    init {
        _muteState.value = getSettingValue(SETTINGS_PREF_KEY_MUTE)
    }

    private fun getSettingValue(key: String): Boolean {
        return getApplication<Application>().getSharedPreferences(
            SETTINGS_PREF,
            Context.MODE_PRIVATE
        )
            .getBoolean(key, false)
    }

    private fun setSettingValue(key: String, value: Boolean? = false) {
        val sharedPref = getApplication<Application>().getSharedPreferences(
            SETTINGS_PREF,
            Context.MODE_PRIVATE
        ) ?: return
        with(sharedPref.edit()) {
            if (value != null) {
                putBoolean(key, value)
            }
            commit()
        }
    }

    fun onClickMute() {
        _muteState.value = !(_muteState.value)!!
        setSettingValue(SETTINGS_PREF_KEY_MUTE, _muteState.value)
    }

    fun onClickReset() {
        resetValues()
    }

    private fun resetValues() {
        _whenValue.value = ""
        _whereValue.value = ""
        _whatValue.value = ""
    }

    fun onClickWhen(view: View) {
        val cardId = Random.nextInt(0, 100)
        Log.d("COMFYKIDS", "onClickWhen $cardId")
        showResult(0, cardId)
        view.isEnabled = false
    }

    fun onClickWhere(view: View) {
        val cardId = Random.nextInt(0, 100)
        Log.d("COMFYKIDS", "onClickWhere $cardId")
        showResult(1, cardId)
//        view.isEnabled = false
    }

    fun onClickWhat(view: View) {
        val cardId = Random.nextInt(0, 100)
        Log.d("COMFYKIDS", "onClickWhat $cardId")
        showResult(2, cardId)
//        view.isEnabled = false
    }

    private fun showResult(cardType: Int, cardId: Int) {
        generateOutputMessage(cardType, cardId).let {
            setOutputMessage(cardType, it)
            speak(it)
        }
    }

    private fun generateOutputMessage(cardType: Int, cardId: Int): String {
        return when (cardType) {
            0 -> RawData.whenSet[cardId % RawData.whenSet.size]
            1 -> RawData.whereSet[cardId % RawData.whereSet.size]
            2 -> RawData.whatSet[cardId % RawData.whatSet.size]
            else -> ""
        }
    }

    private fun setOutputMessage(cardType: Int, message: String) {
        when (cardType) {
            0 -> _whenValue.value = message
            1 -> _whereValue.value = message
            2 -> _whatValue.value = message
        }
    }

    private fun speak(message: String) {
        if (_muteState.value == false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                TtsManager.speak(message)
            }
        }
    }

    companion object {
        private const val SETTINGS_PREF = "settings"
        private const val SETTINGS_PREF_KEY_MUTE = "mute"
    }
}