package com.example.walking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MypageLogin extends AppCompatActivity {
    EditText editid, editPassword;
    TextView sign;
    TextView login;
    Button loginButton;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.kakaologin_activity);

        //회원가입 버튼
        sign = findViewById(R.id.signin);
        editid = findViewById(R.id.editID);
        editPassword = findViewById(R.id.editPassword);
        // 로그인 버튼
        loginButton = findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(v -> {
            if(editid.getText().toString().equals("")){
                Toast.makeText(MypageLogin.this, "아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
            } else if (editPassword.getText().toString().equals("")){
                Toast.makeText(MypageLogin.this, "비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show();
            }else{
                String username = editid.getText().toString();
                String password = editPassword.getText().toString();

                ServerService.login(username, password);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }



        });




        //회원가입 버튼 클릭시, 회원가입 페이지로 이동
        sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, MypageSignup.class);
            startActivity(intent);

        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaologin_activity);


    }
}





//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
//                    @Override
//                    public Unit invoke(Throwable throwable) {
//                        updateKakaoLoginUi(); // 로그아웃 화면만 갱신 함
//                        return null;
//                    }
//                });
//            }
//        });
//
//        updateKakaoLoginUi();
//    }


    // 로그인이 되어 있으면 로그아웃 버튼이 안보이고 로그인이 안되어 잇으면 로그인 버튼이 보임 /
//    private void updateKakaoLoginUi() {
//        //유저 로그인 아이디
//        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
//            @Override
//            public Unit invoke(User user, Throwable throwable) {
//                if (user != null) {
//
//                    Log.i(TAG, "invoke: id=" + user.getId()); //카카오 아이디
//                    Log.i(TAG, "invoke: email=" + user.getKakaoAccount().getEmail()); // 카카오 이메일
//                    Log.i(TAG, "invoke: =" + user.getKakaoAccount().getProfile().getNickname()); // 카카오 닉네임
//                    Log.i(TAG, "invoke: id=" + user.getKakaoAccount().getGender()); // 카카오 성별
//                    Log.i(TAG, "invoke: id=" + user.getKakaoAccount().getAgeRange()); // 카카오 나이
//
//                    nickName.setText(user.getKakaoAccount().getProfile().getNickname()); // 닉네임
//                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage); //카카오 이미지 동그랗게
//                    user.getKakaoAccount().getProfile().getThumbnailImageUrl(); // 카카오 프로파일 사진
//
//
//                    loginButton.setVisibility(View.GONE);
//                    logoutButton.setVisibility(View.VISIBLE);
//                } else {
//                    nickName.setText(null);
//                    profileImage.setImageBitmap(null);
//                    loginButton.setVisibility(View.VISIBLE);
//                    logoutButton.setVisibility(View.GONE);
//                }
//                return null;
//            }
//        });
//    }

