package com.example.walking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

interface PathCallback {
    void run(ArrayList<LatLng> path);
}

interface MapAPI {
    @GET("/map-direction-15/v1/driving")
    Call<JsonObject> getPath(@Query("start") String start, @Query("goal") String goal, @Query("waypoints") String waypoints, @Header("X-NCP-APIGW-API-KEY-ID") String id, @Header("X-NCP-APIGW-API-KEY") String pass);
    @GET("/map-direction-15/v1/driving")
    Call<JsonObject> getPath(@Query("start") String start, @Query("goal") String goal, @Header("X-NCP-APIGW-API-KEY-ID") String id, @Header("X-NCP-APIGW-API-KEY") String pass);
}

public class MapService {
    static final String ID = "sblwrwtxtv";
    static final String Secret = "Wuc9agKMA1dAqHGGgbwkwk8mVsmzd4J8Vw4B84ea";
    static MapAPI mapAPI;
    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        mapAPI = retrofit.create(MapAPI.class);
    }
    public static String point2Parameter(Point p) {
        return "" + p.x + "," + p.y;
    }
    public static String point2Parameter(Point[] ps) {
        if(ps.length == 0) return "";
        String str = point2Parameter(ps[0]);
        for(int i = 1; i < ps.length; i++) {
            str += "|" + point2Parameter(ps[i]);
        }
        return str;
    }
    public static void findPath(Point start, Point end, Point[] ways, PathCallback callback) {
            System.out.println("시작: " + start.x + " " + start.y);
            for(Point way : ways ) {
                System.out.println("경유: " + way.x + " " + way.y);
            }
            System.out.println("끝: " + end.x + " " + end.y);
            System.out.println(point2Parameter(ways));
            Call<JsonObject> req = mapAPI.getPath(point2Parameter(start), point2Parameter(end), point2Parameter(ways), ID, Secret);
            req.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject data = response.body();
                    System.out.println(data);
                    JsonArray array = data.getAsJsonObject("route").getAsJsonArray("traoptimal").get(0).getAsJsonObject().getAsJsonArray("path");
                    ArrayList<LatLng> paths = new ArrayList<>();
                    for(int i = 0; i < array.size(); i++) {
                        String raw = array.get(i).toString();
                        raw = raw.replaceAll("\\[", "");
                        raw = raw.replaceAll("\\]", "");
                        String datas[] = raw.split(",");
                        double y = Double.parseDouble(datas[0]);
                        double x = Double.parseDouble(datas[1]);
                        paths.add(new LatLng(x, y));
                    }
                    callback.run(paths);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("실패:" + t.toString());
                }
            });
            return;
    }




}



