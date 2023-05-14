package com.example.walking;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;


// 카카오 사용포기 삭제할지 말자 오류날까봐 무섭다
public class KakaoApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        KakaoSdk.init(this, "3ecb7b0d17df3214bc8d47398182b50f"); //카카오 SDK 초기화
    }
}
