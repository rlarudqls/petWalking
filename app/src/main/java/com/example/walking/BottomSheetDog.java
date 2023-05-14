package com.example.walking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDog extends BottomSheetDialogFragment {

    View view10;
    Button button3; //네이버 맵 산책 시작 버튼
    View view9;
    WalkFragment fragment;
    BottomSheetDog(WalkFragment fragment) {
        this.fragment = fragment;
    }
    // 반려견 산책 변수
    private ArrayList<View> pages = new ArrayList<>();

    public void changeView(int page) {
        for (View pageView : pages) {
            pageView.setVisibility(View.INVISIBLE);
        }
        pages.get(page).setVisibility(View.VISIBLE);
    }

    public View getPageView(int page) {
        return this.pages.get(page);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottomsheet, container, true);
        pages.add(view.findViewById(R.id.container2_page1));
        pages.add(view.findViewById(R.id.container2_page2));
        pages.add(view.findViewById(R.id.container2_page3));
        changeView(0);

        View page1 = this.getPageView(0); //반려견 선택 페이지
        View page2 = this.getPageView(1); //경로 지점 페이지
        View page3 = this.getPageView(2); //마킹 지점 페이지

        CheckBox checkBox1 = page2.findViewById(R.id.linkseletion_newycheckbox);
        CheckBox checkBox2 = page2.findViewById(R.id.linkseletion_alreadcheckbox);


        view9 = (View) getActivity().findViewById(R.id.NewWalkingStart); //새로운경로
        view10 = (View) getActivity().findViewById(R.id.WalkingStart); // 기존경로

        button3 = (Button) getActivity().findViewById(R.id.WkstartBtn);  //네이버 맵 산책 시작 버튼

        // 경로 선택
        Button button1 = (Button) page2.findViewById(R.id.btn_startwalk); // 경로 지정 버튼

        Button button2 = (Button) page3.findViewById(R.id.btn_loadstartbtn); // 마킹지정 산책 시작 버튼


        CheckBox[] checkBoxes = {checkBox1, checkBox2};
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnClickListener((event) -> {
                if (checkBox.isChecked()) {
                    for (CheckBox otherCheckBox : checkBoxes)
                        if (!otherCheckBox.equals(checkBox)) otherCheckBox.setChecked(false);
                    button1.setEnabled(true);
                } else button1.setEnabled(false);

            });
        }


        // ★★★★ BottomSheetDogSelect 클래스하고 겹친다 설정 건드릴때 확인하자 ★★★★★
        //새로운 경로 산책시작 버튼
        button1.setOnClickListener((event) -> {
            if (checkBox1.isChecked()) {

                /*
                view9.setVisibility(View.VISIBLE);
                button3.setVisibility(View.INVISIBLE);
                AppData.getInstance().pathMode = 2;
               // this.dismiss(); //새로운 경로 산책시작 버튼 클릭시 창 닫힘
                // BottomSheetDogSelect 에서 dog.dismiss(); 사용해야 창이 닫힘 이유를 모르겠음......
                */

            }
        });

        //기존 경로 산책시작 버튼
        if (checkBox2.isChecked()) {

            //this.dismiss();   //기존 경로 체크 산책시작 버튼 클릭시 창 닫힘
            // view10.setVisibility(View.VISIBLE);
            // button3.setVisibility(View.INVISIBLE);
            //AppData.getInstance().pathMode = 1;
        }

        //마킹 지점 산책지점 버튼

        button2.setOnClickListener((event) -> {
            this.dismiss(); // 마킹지정 산책 시작 버튼 클릭시 창 닫힘
            view10.setVisibility(View.VISIBLE);
            button3.setVisibility(View.INVISIBLE);
            AppData.getInstance().pathMode = 1;
            ServerService.pathMarker();

        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDogSelect page1 = new BottomSheetDogSelect();
        page1.init(this);
    }
}