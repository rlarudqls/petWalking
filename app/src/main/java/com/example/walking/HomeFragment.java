package com.example.walking;



import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    boolean isRun;
    EditText WeatherCloud;
    ImageView WeatherImage;
    boolean isShowedWeather = false;

    TextView Now;
    TextView weather_text;
    TextView Temp;
    TextView Clouds;
    TextView Rain;
    TextView Ass;


    TextView humidity;
    TextView nowCloud;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragmentTextView temp
        Runnable task = () -> {
            while(!WeatherService.isAvailable()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.getActivity().runOnUiThread(() -> {
                Weather weather = WeatherService.getWeather();
                //Clear

                //weather.now = new String("Rain");
                String weatherInform = "";
                if(weather.now.equals("Clear")) {
                    weatherInform = weatherInform.concat("구름이 없고");
                } else if(weather.now.equals("Clouds")) {
                    weatherInform = weatherInform.concat("구름이 있고");
                }
                weatherInform = weatherInform.concat(" ");
                if(weather.temp <= -6) {
                    weatherInform = weatherInform.concat("산책을 나가기 에는 너무 추워서 워험하다. 멍! ");
                } else if(weather.temp <= -1) {
                    weatherInform = weatherInform.concat("산책을 하기에 위험 할수 있으므로 주의가 필요하다. 멍! ");
                } else if(weather.temp <= 4) {
                    weatherInform = weatherInform.concat("산책을 하기에 나쁘지는 않다. 멍!");
                } else if(weather.temp <= 10) { // 4 ~ 10
                    weatherInform = weatherInform.concat("산책 하기에 좋다. 멍!");
                } else if(weather.temp <= 15) { // 10 ~ 15
                    weatherInform = weatherInform.concat("산책 하기에 정말 좋다. 멍!");
                } else if(weather.temp <= 20) { // 15 ~ 20
                    weatherInform = weatherInform.concat("산책 나가고 싶다. 멍!");
                } else if(weather.temp <= 25) { //20 ~ 25
                    weatherInform = weatherInform.concat("과격한 활동을 하면 더울수 있다. 멍 !");
                } else if(weather.temp <= 32) { //25 ~ 32
                    weatherInform = weatherInform.concat("더울수 있으니 주의가 필요하다. 멍!");
                } else{ //32 이상
                    weatherInform = weatherInform.concat("산책을 나가기 에는 너무 더워서 워험하다. 멍!");
                }
                if(weather.now.equals("Rain")) {
                    weatherInform = "비가 와서 나갈수 있을까? 멍?";
                }
                ((TextView) view.findViewById(R.id.textview_maxtemp))
                        .setText(String.format("현재 온도 : %.2f ℃", weather.temp));

                ((TextView) view.findViewById(R.id.textView_humidity))
                        .setText(String.format("현재 습도 : %.1f %%", weather.humidity));

                ((TextView) view.findViewById(R.id.textview_nowCloud))
                        .setText(String.format("현재 날씨 : %s", weather.now));
                ((TextView) view.findViewById(R.id.textView_wind))
                        .setText(String.format("현재 바람 : %.1f m/s", weather.wind));
                ((TextView) getActivity().findViewById(R.id.Weather_text))
                        .setText(weatherInform);

            });
        };

        //
        Thread t = new Thread(task);
        t.start();
        return view;
    }
    // 프래그 먼트 에서는 파인트뷰아이디를 사용할수 없으므로
    // EX) 겟엑티비티 를 아래처럼 가져와야 한다


    // API 서버에서 받아오는 속도랑 부팅 속도가 달라서 팅겨서 쓰레드 값 줌



}
