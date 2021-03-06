package com.kccorp.comfykids

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
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
    private var clickCount = 0;

    private val SETTINGS_PREF = "settings"
    private val SETTINGS_PREF_KEY_MUTE = "mute"

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onClickDraw() {
        Log.d("COMFYKIDS", "onClickDraw $clickCount")
        var message = ""
        when (clickCount % 3) {
            0 -> {
                resetValues()
                message =
                    RawData.whenSet[getMessageId(RawData.whenSet)]
                _whenValue.value = message
            }
            1 -> {
                message =
                    RawData.whereSet[getMessageId(RawData.whereSet)]
                _whereValue.value = message
            }

            2 -> {
                message =
                    RawData.whatSet[getMessageId(RawData.whatSet)]
                _whatValue.value = message
            }
        }

        if (_muteState.value == false) {
            TtsManager.speak(message)
        }
        clickCount++
    }

    private fun getMessageId(set: List<String>): Int {
        return Random.nextInt(0, set.size)
    }

    private fun resetValues() {
        clickCount = 0
        _whenValue.value = ""
        _whereValue.value = ""
        _whatValue.value = ""
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onClickCard(view: View) {
        val randomVal = Random.nextInt(-7, 7)
        val button = view as Button
        Log.d("COMFYKIDS", "onClickCard $clickCount ${button.text} $randomVal")
        var message = ""
        when (clickCount % 3) {
            0 -> {
                resetValues()
                message =
                    RawData.whenSet[(Integer.parseInt(button.text as String) + randomVal) % RawData.whenSet.size]
                _whenValue.value = message
            }
            1 -> {
                message =
                    RawData.whereSet[(Integer.parseInt(button.text as String) + randomVal) % RawData.whereSet.size]
                _whereValue.value = message
            }

            2 -> {
                message =
                    RawData.whatSet[(Integer.parseInt(button.text as String) + randomVal) % RawData.whatSet.size]
                _whatValue.value = message
            }
        }

        if (_muteState.value == false) {
            TtsManager.speak(message)
        }
        clickCount++
    }
}