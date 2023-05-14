package com.example.walking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


    public class MypageDogAdd extends AppCompatActivity {

        EditText Dogname,DogYear,DogMonth,Dogday,DogWeight,DogSpecies;
        Button DogAddSub;

        MypageDogAdmin mypageDogAdmin;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mypage_dogadd);

            Dogname = findViewById(R.id.mypage_DogAdd_Name);
            DogYear = findViewById(R.id.mypage_DogAdd_DogBirthYear);
            DogMonth = findViewById(R.id.mypage_DogAdd_DogBirthMonth);
            Dogday = findViewById(R.id.mypage_DogAdd_DogBirthDay);
            DogWeight = findViewById(R.id.mypage_DogAdd_DogWeight);
            DogSpecies = findViewById(R.id.mypage_DogSpecies);
            DogAddSub = findViewById(R.id.mypage_DogAdd_sub);

            DogAddSub.setOnClickListener(v -> {
                if(Dogname.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 이름을 입력해주세요.",Toast.LENGTH_LONG).show();
                }else if(DogYear.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 년도를 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else if(DogMonth.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 월을 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else if(Dogday.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 일을 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else if(DogWeight.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 몸무게를 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else if(DogSpecies.getText().toString().equals("")){
                    Toast.makeText(MypageDogAdd.this, "반려견 종을 입력해주세요.",Toast.LENGTH_LONG).show();
                }

                else{
                    ServerService.dogCreate(Dogname.getText().toString(), DogYear.getText().toString() + "년 " + DogMonth.getText().toString() + " 월" + Dogday.getText().toString() + " 일", DogSpecies.getText().toString());


                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }






                    });








            // 반련견 추가 페이지 오토컴플레이트 텍스트 뷰 -> 반련견 종 관련
            List<String> list;
            list = new ArrayList<String>();


            final AutoCompleteTextView autoCompleteTextView =
                    (AutoCompleteTextView) findViewById(R.id.mypage_DogSpecies);

            autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, list));
            list.add("포메라니안"); list.add("진돗개"); list.add("푸들");
            list.add("닥스훈트"); list.add("셰퍼드"); list.add("리트리버");
            list.add("허스키"); list.add("불도그"); list.add("핏불 테리어");
            list.add("치와와"); list.add("도베르만"); list.add("퍼그");
            list.add("시바견"); list.add("로트바일러"); list.add("시추");
            list.add("비글"); list.add("차우차우"); list.add("말티즈");
            list.add("카네코르소"); list.add("보더 콜리"); list.add("말티푸");
            list.add("복서"); list.add("사모예드"); list.add("스피츠");
            list.add("웰시코기"); list.add("불 테리어"); list.add("요크셔 테리어");
            list.add("비숑 프리제"); list.add("아키타"); list.add("슈나우저");
            list.add("달마티안"); list.add("미니어처 핀셔"); list.add("시츄");
            list.add("빠삐용"); list.add("그레이 하운드"); list.add("쿠바스");
            list.add("비즐라"); list.add("차우차우"); list.add("콜리");
            list.add("실키테리어"); list.add("스키퍼키"); list.add("파라오하운드");




        }
    }






