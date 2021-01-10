package com.kccorp.comfykids

import android.app.Application
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _whenValue = MutableLiveData("")
    private val _whereValue = MutableLiveData("")
    private val _whatValue = MutableLiveData("")
    private var clickCount = 0;
    val whenValue: LiveData<String> = _whenValue
    val whereValue: LiveData<String> = _whereValue
    val whatValue: LiveData<String> = _whatValue

    fun onClickReset() {
        resetValues()
    }

    private fun resetValues() {
        clickCount = 0
        _whenValue.value = ""
        _whereValue.value = ""
        _whatValue.value = ""
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onClickCard(view: View) {
        val button = view as Button
        Log.d("COMFYKIDS", "onClickCard $clickCount ${button.text}")
        var message = ""
        when (clickCount % 3) {
            0 -> {
                resetValues()
                message = whenSet[Integer.parseInt(button.text as String) % whenSet.size]
                _whenValue.value = message
            }
            1 -> {
                message = whereSet[Integer.parseInt(button.text as String) % whereSet.size]
                _whereValue.value = message
            }

            2 -> {
                message =
                    whatSet[Integer.parseInt(button.text as String) % whatSet.size]
                _whatValue.value = message
            }
        }

        TtsManager.speak(message)
        clickCount++
    }

    companion object {
        val whenSet = listOf<String>(
            "4월에",
            "11월에",
            "여름에",
            "새벽에",
            "석 달 후에",
            "12월에",
            "8월에",
            "교실에서",
            "가을에",
            "매월 1일에",
            "집에서",
            "길에서",
            "모레",
            "학교에서",
            "병원에서",
            "교문에서",
            "운동장에서",
            "화장실에서",
            "6월에",
            "아침에",
            "계단에서",
            "봄에",
            "비 올 때",
            "5월에",
            "생일에",
            "점심에",
            "친구 집에서",
            "11월에",
            "교회에서",
            "두 달 후에"
        )

        val whereSet = listOf<String>(
            "코 파다가",
            "운동 하다가",
            "폰 보다가",
            "밥 먹다가",
            "체조 하다가",
            "피구  하다가",
            "노래 듣다가",
            "누워있다가",
            "유튜브 보다가",
            "꿈 꾸다가",
            "신발 신다가",
            "춤 추다가",
            "뛰어 가다가",
            "짝이랑 놀다가",
            "물 먹다가",
            "똥 싸다가",
            "놀다가",
            "쉬다가",
            "TV 보다가",
            "게임 하다가",
            "공부 하다가",
            "책 읽다가",
            "걸어 가다가",
            "앉아 있다가",
            "축구 하다가",
            "물 먹다가",
            "피자 먹다가",
            "하늘 보다가",
            "집에 가다가",
            "학교 가다가"
        )
        val whatSet = listOf<String>(
            "사랑을 만난다",
            "고백을 받는다",
            "돈을 줍는다",
            "로또 당첨된다",
            "칭찬을 받는다",
            "키가 큰다",
            "건강해지다",
            "똥을 잘 싸게 된다",
            "변비에 걸린다",
            "피구왕이 된다",
            "맛있는 음식을 먹는다",
            "BTS를 만난다",
            "넘어진다",
            "물벼락을 맞는다",
            "연예인을 만난다",
            "문화상품권을 받는다",
            "상을 받는다",
            "성적이 오른다",
            "새 휴대폰이 생긴다",
            "첫사랑을 만난다",
            "친구와 떡볶이를 먹게된다",
            "행복한 일이 생긴다",
            "큰 복이 찾아온다",
            "하늘에서 피자가 떨어진다",
            "행복한 일이 생긴다",
            "코로나가 종식된다",
            "마음이 잘 맞는 친구가 생긴다",
            "귀인을 만난다",
            "감기에 걸린다",
            "선생님을 만난다"
        )
    }
}