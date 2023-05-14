package com.example.walking;

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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ServerAPI {
    @GET("/register")
    Call<String> register(@Query("username") String username,
                          @Query("password") String password,
                          @Query("email") String email);
    @GET("/login")
    Call<JsonObject> login(@Query("username") String username, @Query("password") String password);
    @GET("/dog/create")
    Call<Void> dogCreate(@Query("name") String name, @Query("sessionId") int sessionId, @Query("birth") String birth, @Query("species") String species);
    @GET("/session/dog")
    Call<JsonArray> sessionDog(@Query("sessionId") int sessionId);
    @GET("/marker/create")
    Call<Void> markerCreate(@Query("location") String location, @Query("type") String type, @Query("pathId") String pathId);

    @GET("/path/marker")
    Call<JsonArray> pathMarker(@Query("pathId") String pathId);
    @GET("/path/create")
    Call<JsonObject> pathCreate(@Query("dogId") String dogId);
    @GET("/dog/path")
    Call<JsonArray> dogPath(@Query("dogId") String dogId);
    @GET("/record/create")
    Call<Void> recordCreate(@Query("dogId") String dogId, @Query("data") String data);
    @GET("/record/")
    Call<JsonArray> dogRecord(@Query("dogId") String dogId);
    @DELETE("/dog/")
    Call<Void> dogDelete(@Query("dogId") String dogId);


}

public class ServerService {
    static ServerAPI serverAPI;


    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        serverAPI = retrofit.create(ServerAPI.class);
    }
    public static void dogDelete(String dogId) {
        Call<Void> req = serverAPI.dogDelete(dogId);
        req.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }
    public static void register(String username, String password, String email) {

        Call<String> req = serverAPI.register(username, password, email);
        req.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String data = response.body();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("실패:" + t.toString());
                }
            });
    }
    public static void login(String username, String password) {

        Call<JsonObject> req = serverAPI.login(username, password);
        req.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject data = response.body();
                AppData.getInstance().sessionId = data.get("sessionId").getAsNumber().intValue();
                //테스트용
                //ServerService.dogCreate("치킨댕댕이", "2012년 9월 10일", "판다");
                ServerService.sessionDog();
                //ServerService.pathMarker("6332e8f6e2718f92f9a2496e");

                System.out.println(data);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }
    public static void dogCreate(String name, String birth,  String species) {


        Call<Void> req = serverAPI.dogCreate(name, AppData.getInstance().sessionId, birth, species);
        req.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sessionDog();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }
    public static void sessionDog() {

        Call<JsonArray> req = serverAPI.sessionDog(AppData.getInstance().sessionId);
        req.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                AppData.getInstance().removeAllDog();
                for(int i = 0; i < data.size(); i++) {
                    JsonObject object = data.get(i).getAsJsonObject();
                    String id = object.get("id").getAsString();
                    String name = object.get("name").getAsString();
                    String birth = object.get("birth").getAsString();
                    String species = object.get("species").getAsString();
                    AppData.getInstance().addDog(new DogItem(id, name, birth, species, R.drawable.ic_dog_24dp));
                }
                System.out.println(data);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }

    public static void markerCreate() {

        ArrayList<DogMarker> dogMarkers = AppData.getInstance().markers;
        String pathId = AppData.getInstance().selectedPathId;
        for(DogMarker marker : dogMarkers) {
            Call<Void> req = serverAPI.markerCreate(marker.location.toString(), marker.type, pathId);
            req.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("마커 만들기 성공");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("실패:" + t.toString());
                }
            });
        }
        dogMarkers.clear();
    }
    @GET("/path/marker")
    public static void pathMarker() {

        String pathId = AppData.getInstance().selectedPathId;
        Call<JsonArray> req = serverAPI.pathMarker(pathId);
        req.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                for(int i = 0; i < data.size(); i++) {
                    JsonObject obj = data.get(i).getAsJsonObject();
                    String id = obj.get("id").getAsString();
                    String locationStr = obj.get("location").getAsString();
                    locationStr = locationStr.replaceAll("LatLng\\{latitude=", "");
                    locationStr = locationStr.replaceAll("longitude=", "");
                    locationStr = locationStr.replaceAll("\\}", "");
                    locationStr = locationStr.replaceAll(" ", "");
                    String[] ll = locationStr.split(",");
                    LatLng location = new LatLng(Double.parseDouble(ll[0]), Double.parseDouble(ll[1]));
                    String type =  obj.get("type").getAsString();
                    AppData.getInstance().markers.add(new DogMarker(location, type));
                }
                System.out.println(data);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }
    @GET("/path/create")
    //pathCreate -> markerCreate
    public static void pathCreate() {

        String dogId = AppData.getInstance().selectedDogId;
        Call<JsonObject> req = serverAPI.pathCreate(dogId);

        req.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("가나다" + response.body());
                AppData.getInstance().selectedPathId = response.body().get("id").getAsString();
                markerCreate();
                System.out.println("경로 만들기 성공");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }
    @GET("/dog/path")
    public static void dogPath() {

        String dogId = AppData.getInstance().selectedDogId;
        Call<JsonArray> req = serverAPI.dogPath(dogId);
        req.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                AppData.getInstance().removeAllPath();
                for(int i = 0; i < data.size(); i++) {
                    String id = data.get(i).getAsJsonObject().get("id").getAsString();
                    String date =  data.get(i).getAsJsonObject().get("date").getAsString();
                    AppData.getInstance().addPath(new PathItem(id, date));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }

    @GET("/path/create")
    //pathCreate -> markerCreate
    public static void recordCreate(@Query("dogId") String dogId, @Query("data") String data) {

        Call<Void> req = serverAPI.recordCreate(dogId, data);

        req.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { ;
                System.out.println("기록 만들기 성공");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("기록 만들기 실패:" + t.toString());
            }
        });
    }
    @GET("/record")
    public static void dogRecord() {
        String dogId = AppData.getInstance().selectedWalkDogId;
        Call<JsonArray> req = serverAPI.dogRecord(dogId);
        req.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                AppData.getInstance().removeAllRecord();
                for(int i = 0; i < data.size(); i++) {
                    String dataraw =  data.get(i).getAsJsonObject().get("data").getAsString();
                    String[] args = dataraw.split(",");
                    String startTime = args[0];
                    String endTime = args[1];
                    String bottom = args[2];
                    AppData.getInstance().addRecord(new RecordItem(startTime, endTime, bottom));
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println("실패:" + t.toString());
            }
        });
    }

}