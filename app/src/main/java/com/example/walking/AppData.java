package com.example.walking;

import android.widget.BaseAdapter;

import com.naver.maps.geometry.LatLng;

import java.nio.file.Path;
import java.util.ArrayList;
class DogMarker {
    LatLng location;
    String type;
    DogMarker(LatLng location, String type) {
        this.location = location;
        this.type = type;
    }
}
public class AppData {
    private static AppData instance;
    public int pathMode = 0; // 1, 2
    public int sessionId = -1;
    public String selectedPathId;
    public String selectedDogId;
    public String selectedWalkDogId;
    public double dist;
    public String startTime;
    public long startTimeMillis;
    public String endTime;
    public long endTimeMillis;
    public LatLng endLocation;



    public ArrayList<DogMarker> markers = new ArrayList<>();
    //Paths;

    public static AppData getInstance() {
        if(instance == null) {
            instance = new AppData();
        }
        return instance;
    }
    //로그인 및 각종 데이터 중간 저장 가능
    // 강아지 리스트뷰 도그 아이템 중간 저장소
    // 중간 데이터 저장소 없으니 오류남 ;;;


    //MypageDogAdmin 마이페이지 강아지추가 리스트 뷰
    private ArrayList<DogItem> dogItems = new ArrayList<>();
    public void addDog(DogItem dogItem) {
        dogItems.add(dogItem);
    }
    public void removeDog(DogItem dogItem) {
        dogItems.remove(dogItem);
    }
    public void removeAllDog() {
        dogItems.clear();
    }
    public ArrayList<DogItem> getAllDogItems() {
        return dogItems;
    }

    private ArrayList<PathItem> pathItems = new ArrayList<>();
    public void addPath(PathItem pathItem) {
        pathItems.add(pathItem);
    }
    public void removePath(PathItem pathItem) {
        pathItems.remove(pathItem);
    }
    public void removeAllPath() {
        pathItems.clear();
    }
    public ArrayList<PathItem> getAllPathItems() {
        return pathItems;
    }
    //BottomSheetDogSelect 산책페이지 강아지 선택 리스트뷰

    private ArrayList<RecordItem> recordItems = new ArrayList<>();
    public void addRecord(RecordItem recordItem) {

        recordItems.add(recordItem);
        recordUpdate();
    }
    public void removeRecord(RecordItem recordItem) {

        recordItems.remove(recordItem);
        recordUpdate();
    }
    public void removeAllRecord() {
        recordItems.clear();
        recordUpdate();
    } // 개선 옵저벼 패턴

    public ArrayList<RecordItem> getAllRecordItems() {
        return recordItems;
    }
    public BaseAdapter recordAapter;
    public void recordUpdate() {
        if(recordAapter != null) {
            recordAapter.notifyDataSetChanged();
        }

    }




}
