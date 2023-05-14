package com.example.walking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.IpSecManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

// 서버도 이런식으로 가능 할려나?

//기상 API 제이슨 형태로 정보 부르고 받기
interface WeatherAPI {
    @GET("/data/2.5/weather")
    Call<JsonObject> getForecast(@Query("appid") String appid, @Query("lat") String lat, @Query("lon") String lon);
}

public class WeatherService {
    static final String appid = "8be889a2516aab4e4426611676515fd0";
    static WeatherAPI weatherAPI;
    static Weather lastWeather;


    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    public static boolean isAvailable() {
        return lastWeather != null;
    }
    public static void updateWeatherFromLocation(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Call<JsonObject> req = weatherAPI.getForecast(appid, String.valueOf(latitude), String.valueOf(longitude));
            req.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject data = response.body();
                    Weather weather = new Weather();
                    // 온도, 구름, 바람, 습도
                    weather.temp = data.get("main").getAsJsonObject().get("temp").getAsFloat() - 273.15f;
                    weather.humidity = data.get("main").getAsJsonObject().get("humidity").getAsFloat();
                    weather.now = data.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
                    weather.wind = data.get("wind").getAsJsonObject().get("speed").getAsFloat();
                    lastWeather = weather;
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("실패:" + t.toString());
                }
            });
            return;
    }
    // 앱 권한 및 위치정보
    public static void updateWeatherNow(MainActivity mainActivity) {
        Context context = mainActivity.getApplicationContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( mainActivity, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lo = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lo != null) updateWeatherFromLocation(lo);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,(Location location) -> {
            updateWeatherFromLocation(location);
        });


    }




    public static Weather getWeather() {
        if (!isAvailable()) {
            return null;
        }
        return lastWeather;
    }

}


class Weather {
    float temp;
    float humidity;
    float wind;
    String now;

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", humidity=" + humidity +
                ", wind=" + wind +
                ", now='" + now + '\'' +
                '}';
    }
}



