package com.zoo.dongyeah

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "8b741c84768cf91876d5d32e112de1ef")
    }
}