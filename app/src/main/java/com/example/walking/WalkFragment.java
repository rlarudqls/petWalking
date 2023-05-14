package com.example.walking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.SystemClock;
import android.os.health.SystemHealthManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.lang.reflect.Array;
import java.security.Permission;
import java.security.Permissions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;


public class WalkFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private MapView mapView;
    private double lat, lon;
    private double lat1, lon1; // 다이렉션5 길찾기 한 좌표

    //상태 정보 (시발 좆됨 ...... 수정 꼭 필요,.,,,,,)


    //산책 버튼 구현
    private Button playbutton, stopbutton, WkstartBtn, peebutton, poobutton, pausebutton, endbutton, newstopbutton, newpeebutton, newpoobutton;
    private TextView stopwatchtext;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    int level  = 0;

    private CheckBox alreadycheckBox;

    // 마커 표현
    Marker marker = new Marker();
    CheckBox checkBox1;
    CheckBox checkBox2;
    BottomSheetDog bottomSheetDog;
    TextView textView;

    View view9;
    View view10;

    private Button btn_open_bt_sheet;
    private TextView text_result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk, container, false);
        // Inflate the layout for this fragment
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); // 현재 위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); // 현재 위치 마크 모양

        //네이버 경로선과 화살표
        PathOverlay path = new PathOverlay();

        // naverMap.getMaxZoom(); 줌 초기화?

        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        btn_open_bt_sheet = getActivity().findViewById(R.id.WkstartBtn);
        Thread thread = new Thread(() -> {
            while(true) {
                if(this.isDetached()) {
                    break;
                }
                if(this.isVisible()) {
                    this.getActivity().runOnUiThread(() -> {
                        if (AppData.getInstance().pathMode != 0 || AppData.getInstance().sessionId == -1) {
                            btn_open_bt_sheet.setVisibility(View.INVISIBLE);
                        } else {

                            btn_open_bt_sheet.setVisibility(View.VISIBLE);
                        }
                    });
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // 시발 좆됨 ...... 수정 꼭 필요,.,,,,,
        btn_open_bt_sheet.setOnClickListener((View v) -> {
            bottomSheetDog = new BottomSheetDog(this);
            bottomSheetDog.show(getActivity().getSupportFragmentManager(), null);

            //네이버맵 산책시작 버튼
            WkstartBtn = getActivity().findViewById(R.id.WkstartBtn);
            //기존경로 버튼
            playbutton = getActivity().findViewById(R.id.playbutton);
            stopbutton = getActivity().findViewById(R.id.stopbutton);
            // peebutton = getActivity().findViewById(R.id.peebutton);
            // poobutton = getActivity().findViewById(R.id.poobutton);
            pausebutton = getActivity().findViewById(R.id.pausebutton);

            // 스톱워치 텍스트
            stopwatchtext = getActivity().findViewById(R.id.stopwatch);
            // 시간
            chronometer = getActivity().findViewById(R.id.chronometer);
            chronometer.setText("00:00:00");

            newpeebutton = getActivity().findViewById(R.id.newpeebutton);
            newpoobutton = getActivity().findViewById(R.id.newpoobutton);
            newstopbutton = getActivity().findViewById(R.id.newstopbutton);
            // 크로메틱(시간간)
            chronometer.setOnChronometerTickListener((chronometer) -> {
                long t = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.setText(DateFormat.format("kk:mm:ss", t));

                view9 = (View) getActivity().findViewById(R.id.NewWalkingStart);
                view10 = (View) getActivity().findViewById(R.id.WalkingStart);
                //새로운경로 버튼


            });

            ArrayList<Marker> storedMarkers = new ArrayList<>();
            //맵 클릭시 도착지 설정
            //경로선 과 화살표
            PathOverlay path = new PathOverlay();
            // 스타트 버튼 클릭시
            playbutton.setOnClickListener((view) -> {
                view.setVisibility(View.GONE);
                pausebutton.setVisibility(View.VISIBLE);
                ArrayList<DogMarker> markers = AppData.getInstance().markers;
                for(DogMarker marker : markers) {
                    Marker newMarker = new Marker();
                    storedMarkers.add(newMarker);
                    newMarker.setWidth(80);
                    newMarker.setHeight(80);
                    //마커 이미지 변경
                    if(marker.type.equals("want"))
                        newMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_trash));
                    else if(marker.type.equals("poo")) {
                        newMarker.setIcon(OverlayImage.fromResource((R.drawable.ic_poo_adobe_express)));
                    }
                    else if(marker.type.equals("pee")) {
                        newMarker.setIcon(OverlayImage.fromResource((R.drawable.ic_icons8_wa)));
                    }
                    //현재 내위치에 마커 찍기
                    newMarker.setPosition(marker.location);
                    // 마커 표시 // 마커 삭제 원할시 marker.setMap(null);
                    newMarker.setMap(naverMap);
                }
                // 경로선 테스트 ,
                Point start = new Point(lon, lat); // 현재 위치 A 출발지
                Point end = new Point(AppData.getInstance().endLocation.longitude, AppData.getInstance().endLocation.latitude);

                ArrayList<Point> points  = new ArrayList<>();
                points.add(start);
                for(DogMarker marker : markers) points.add(new Point(marker.location.longitude , marker.location.latitude));
                points.add(end);

                Point[] pointArr = new Point[points.size()];
                points.toArray(pointArr);
                //System.out.println("테스트" + Math.min(5, Math.max(points.size() - 2, 0)));
                ArrayList<Integer> wayIndexes = RoadAlgorithm.run(pointArr, 0.05 , Math.min(5, Math.max(points.size() - 2, 0))); // 최대 거리 , 경유지 갯수
                System.out.println(wayIndexes);
                if(wayIndexes != null && !wayIndexes.isEmpty())wayIndexes.remove(0);
                if(wayIndexes != null && !wayIndexes.isEmpty())wayIndexes.remove(wayIndexes.size() - 1);
                ArrayList<Point> ways = new ArrayList<>();
                if(wayIndexes != null) for(int index : wayIndexes)  ways.add(points.get(index));
                ways = RoadAlgorithm.TSP(start, end, ways);
                int dd = 0;
                int[] ids = {
                        R.drawable.ic_looks_one_fill0_wght400_grad0_opsz48,
                        R.drawable.ic_looks_two_fill0_wght400_grad0_opsz48,
                        R.drawable.ic_looks_3_fill0_wght400_grad0_opsz48,
                        R.drawable.ic_looks_4_fill0_wght400_grad0_opsz48,
                        R.drawable.ic_looks_5_fill0_wght400_grad0_opsz48
                };
                for(Point p : ways) {

                    Marker newMarker = new Marker();
                    newMarker.setWidth(80);
                    newMarker.setHeight(80);
                    newMarker.setPosition(new LatLng(p.y, p.x));

                    newMarker.setIcon(OverlayImage.fromResource((ids[dd])));
                    storedMarkers.add(newMarker);
                    newMarker.setMap(naverMap);
                    ++dd;
                }

                Point[] wayArr = new Point[ways.size()];
                ways.toArray(wayArr);

                MapService.findPath(start, end, wayArr, (paths) -> {

                    double dist = 0;
                    for(int i = 1; i < paths.size(); i++) {
                        Location a = new Location("");
                        Location b = new Location("");
                        LatLng aa = paths.get(i - 1);
                        LatLng bb = paths.get(i);

                        a.setLatitude(aa.latitude);
                        a.setLongitude(aa.longitude);
                        b.setLatitude(bb.latitude);
                        b.setLongitude(bb.longitude);

                        double midDist = a.distanceTo(b);
                        dist += midDist;
                    }
                    AppData.getInstance().dist = dist;
                    ((TextView)this.getActivity().findViewById(R.id.walklenge)).setText(String.format("%.2fKM", dist / 1000));
                    Date startDate = Calendar.getInstance().getTime();
                    AppData.getInstance().startTimeMillis = Calendar.getInstance().getTimeInMillis();
                    LocalDateTime dateTime = LocalDateTime.now();
                    AppData.getInstance().startTime = String.format("%04d-%02d-%02d %02d:%02d", dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
                    path.setCoords(paths);
                    path.setMap(naverMap); // 경로선 생성
                });


                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
            });

            //퍼즈 버튼 클릭시
            pausebutton.setOnClickListener((view) -> {
                view.setVisibility(View.GONE);
                playbutton.setVisibility(View.VISIBLE);

                // Toast.makeText(this,"산책을 중지 했습니다.",Toast.LENGTH_LONG).show();

                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            });

            // 기존경로 정지 버튼 클릭시
            stopbutton.setOnClickListener((view) -> {
                view.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("안내");
                builder.setMessage("산책을 종료하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "산책을 끝냈습니다. ", Toast.LENGTH_SHORT).show();
                        Date endDate = Calendar.getInstance().getTime();
                        LocalDateTime dateTime = LocalDateTime.now();
                        AppData.getInstance().endTime = String.format("%04d-%02d-%02d %02d:%02d", dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
                        AppData.getInstance().endTimeMillis = Calendar.getInstance().getTimeInMillis();
                        AppData appdata = AppData.getInstance();
                        long gap = (appdata.endTimeMillis - appdata.startTimeMillis) / 1000;
                        ServerService.recordCreate(AppData.getInstance().selectedDogId, appdata.startTime + "," + appdata.endTime + ","+ String.format("총시간: %02d:%02d 총길이: %.2fKM 마킹횟수: %d", gap / 60, gap % 60, appdata.dist/1000, appdata.markers.size()));
                        for(Marker marker : storedMarkers) {
                            marker.setMap(null);
                        }
                        storedMarkers.clear();
                        playbutton.setVisibility(View.VISIBLE);
                        pausebutton.setVisibility(View.GONE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        pauseOffset = 0;
                        chronometer.stop();
                        chronometer.setText("00:00:00");
                        running = false;
                        path.setMap(null); //경로선 제거거
                        bottomSheetDog.view10.setVisibility(View.INVISIBLE); // 대박 ;;; 개고생함
                        WkstartBtn.setVisibility(View.VISIBLE);
                        AppData.getInstance().markers.clear();
                        AppData.getInstance().pathMode = 0;


                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "산책을 계속합니다.. ", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            });

            naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
                Marker before;

                @Override
                public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

                    if (AppData.getInstance().pathMode == 1) {
                        Toast.makeText(getActivity().getApplicationContext(), "도착지가 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                        if (before != null) before.setMap(null);
                        Marker endMarker = new Marker();
                        endMarker.setWidth(80);
                        endMarker.setHeight(80);
                        //마커 이미지 변경
                        endMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_cloud));
                        endMarker.setPosition(latLng);
                        AppData.getInstance().endLocation = latLng;
                        endMarker.setMap(naverMap);
                        storedMarkers.add(endMarker);
                        before = endMarker;

                    }
                    if (AppData.getInstance().pathMode == 2) {

                        Toast.makeText(getActivity().getApplicationContext(), "마킹이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                        if (before != null) before.setMap(null);
                        Marker endMarker = new Marker();

                        storedMarkers.add(endMarker);

                        endMarker.setWidth(80);
                        endMarker.setHeight(80);
                        //마커 이미지 변경 // 이미지 변경 꼭 필요함 수정 필요
                        endMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_trash));
                        AppData.getInstance().markers.add(new DogMarker(latLng, "want"));
                        endMarker.setPosition(latLng);
                        endMarker.setMap(naverMap);
                        // before = endMarker;



                    }

                }

            });
            newpoobutton.setOnClickListener((view) -> {

                // marker.setPosition(new LatLng(this,LOCATION_PERMISSION_REQUEST_CODE));
                // 마커 아이콘 이미지
                //marker.setIcon(OverlayImage.fromResource(R.drawable.marker_icon));
                // 마커 크기
                Marker newMarker = new Marker();
                storedMarkers.add(newMarker);
                newMarker.setWidth(80);
                newMarker.setHeight(80);
                //마커 이미지 변경
                newMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_poo_adobe_express));
                //현재 내위치에 마커 찍기
                LatLng location = new LatLng(lat, lon);
                AppData.getInstance().markers.add(new DogMarker(location, "poo"));
                newMarker.setPosition(location);
                // 마커 표시 // 마커 삭제 원할시 marker.setMap(null);
                newMarker.setMap(naverMap);
                newMarker.setOnClickListener((viewOverlay) -> {
                    newMarker.setMap(null);
                    return false;

                });

            });


            newpeebutton.setOnClickListener((view) -> {
                // marker.setPosition(new LatLng(this,LOCATION_PERMISSION_REQUEST_CODE));
                // 마커 아이콘 이미지
                //marker.setIcon(OverlayImage.fromResource(R.drawable.marker_icon));
                // 마커 크기
                Marker newMarker = new Marker();
                storedMarkers.add(newMarker);
                newMarker.setWidth(80);
                newMarker.setHeight(80);
                //마커 이미지 변경
                newMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_icons8_wa));
                LatLng location = new LatLng(lat, lon);
                AppData.getInstance().markers.add(new DogMarker(location, "pee"));
                //현재 내위치에 마커 찍기
                newMarker.setPosition(location);
                // 마커 표시 // 마커 삭제 원할시 marker.setMap(null);
                newMarker.setMap(naverMap);
                newMarker.setOnClickListener((viewOverlay) -> {
                    newMarker.setMap(null);
                    return false;
                });
            });

            // 정지 버튼 클릭시


            newstopbutton.setOnClickListener((view) -> {
                // 알람이 가게끔
                Marker newMarker = new Marker();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("안내");
                builder.setMessage("마킹 저장을 종료하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "마킹이 저장되었습니다. ", Toast.LENGTH_SHORT).show();

                        for (Marker marker : storedMarkers) {
                            marker.setMap(null);
                        }
                        ServerService.pathCreate();
                        storedMarkers.clear();
                        bottomSheetDog.view9.setVisibility(View.INVISIBLE); // 와우 ..... 찾음
                        WkstartBtn.setVisibility(View.VISIBLE);
                        AppData.getInstance().pathMode = 0;

                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "마킹 저장을 계속합니다. ", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });


        });


    }
}