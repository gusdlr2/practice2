package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) { // login이 안될시 다른 activity로 이동
            myStartSignUpActivity();
        }

        findViewById(R.id.logout).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() { // 버튼 눌렀을떄 작동되는지 test
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();// 로그아웃 기능
                    myStartSignUpActivity();
                    //Log.e("test","test");
                    break;
            }
        }
    };

    private void myStartSignUpActivity() { // 다른 activity로 이동이 가능하다
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}