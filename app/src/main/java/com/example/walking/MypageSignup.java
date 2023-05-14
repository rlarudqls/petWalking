package com.example.walking;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MypageSignup extends AppCompatActivity {
    EditText name,id,pw,pw2,email,birthyear,birthdate,birthday;
    Button pwcheck, submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //기입 항목
        name = findViewById(R.id.signName);
        id=findViewById(R.id.signID);
        pw=findViewById(R.id.signPW);
        pw2=findViewById(R.id.signPW2);
        email=findViewById(R.id.signmail);
        birthyear=findViewById(R.id.signBirth);
        birthdate=findViewById(R.id.signBirth2);
        birthday=findViewById(R.id.signBirth3);

        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if(pw.getText().toString().equals(pw2.getText().toString())){
                pwcheck.setText("일치");
            }else{
                Toast.makeText(MypageSignup.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        //회원가입 완료 버튼
        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(v -> {

            if(name.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "이름을 입력해주세요.",Toast.LENGTH_LONG).show();
            } else if (id.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "아이디을 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (birthyear.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "년을 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (birthdate.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "월을 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (birthday.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "일을 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (pw.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (pw2.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "비밀번호 확인을 입력해주세요.",Toast.LENGTH_LONG).show();
            }
            else if (email.getText().toString().equals("")){
                Toast.makeText(MypageSignup.this, "이메일을 입력해주세요.",Toast.LENGTH_LONG).show();
            }

            else{
                ServerService.register(id.getText().toString(), pw.getText().toString(),email.getText().toString());

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });



//            if(pw.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }
//
//            if(pw2.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }
//
//            if(email.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }
//
//            if(birthyear.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }
//
//            if(birthdate.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }
//
//            if(birthday.getText().toString()==null){
//                Toast.makeText(MypageSignup.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
//            } else {
//            }






    }




}
