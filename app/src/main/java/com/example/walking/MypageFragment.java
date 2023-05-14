package com.example.walking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class MypageFragment extends Fragment {
    public TextView textView;
    private TextView tv_outPut;
    Button kakaoLoginBtn;
    Button mypageWalkdiaryBtn;
    Button mypageDogAdminBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);



        //엑티브 화면 버튼 화면전환
        kakaoLoginBtn = (Button) view.findViewById(R.id.mypage_loginbt);
        mypageWalkdiaryBtn = (Button) view.findViewById(R.id.mypage_walkdiarybtn);
        mypageDogAdminBtn = (Button)view.findViewById(R.id.mypage_dogbt);
        kakaoLoginBtn.setOnClickListener(v -> {
            if(AppData.getInstance().sessionId == -1) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MypageLogin.class);
                startActivity(intent);
            } else {
                AppData.getInstance().sessionId = -1;
            }
        });

        // 마이페이지 반려견 관리
        mypageDogAdminBtn.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), MypageDogAdmin.class);
            startActivity(intent);

        });

        // 마이페이지 산책일지
        mypageWalkdiaryBtn.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), Tabwalk.class);
            startActivity(intent);

        });
        MypageFragment parent = this;
        Thread thread = new Thread(() -> {
                while (true) {
                    if (parent.isDetached()) {
                        break;
                    }

                    if (parent.isVisible()) {
                        parent.getActivity().runOnUiThread(() -> {
                            if (AppData.getInstance().sessionId == -1) {
                                kakaoLoginBtn.setText("로그인");
                                mypageDogAdminBtn.setVisibility(View.INVISIBLE);
                                mypageWalkdiaryBtn.setVisibility(View.INVISIBLE);

                            } else {
                                kakaoLoginBtn.setText("로그아웃");
                                mypageDogAdminBtn.setVisibility(View.VISIBLE);
                                mypageWalkdiaryBtn.setVisibility(View.VISIBLE);
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
        //마이페이지 로그인



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}

