package com.kccorp.comfykids

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.annotation.RequiresApi
import java.util.*


object TtsManager {
    private lateinit var mTts: TextToSpeech
    private var mContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initialize(context: Context) {
        mContext = context
        mTts = TextToSpeech(mContext) { state ->
            if (state == TextToSpeech.SUCCESS) {
                mTts.language = Locale.getDefault()
//                speak(context.getString(R.string.initialized))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun speak(text: String) {
        val utteranceId = mContext.hashCode().toString() + ""
        mTts.speak(text, TextToSpeech.QUEUE_ADD, null, utteranceId)
    }

    fun destroy() {
        if (mTts.isSpeaking) {
            mTts.stop()
        }
        mTts.shutdown()
    }
}