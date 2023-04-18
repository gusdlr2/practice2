package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// test1
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginbutton).setOnClickListener(onClickListener);
    }

    private void login() {
        String email = ((EditText) findViewById(R.id.emailcheck)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if(email.length() > 0 && password.length() > 0 ){

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //Log.d(TAG,"success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("로그인에 성공했습니다");
                                    myStartMainActivity();


                                } else {
                                    //Log.w(TAG,"fail",task.getException());
                                    if(task.getException() != null){ // null 값을 입력시
                                        startToast(task.getException().toString());
                                    }

                                }
                            }
                        });

        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }


    }

    View.OnClickListener onClickListener = new View.OnClickListener() { // 버튼 눌렀을떄 작동되는지 test
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginbutton:
                    login();
                    Log.e("test","test");
                    break;
            }
        }
    };


    private void startToast(String msg){ // 메세지 전달
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void myStartMainActivity() { // 다른 activity로 이동이 가능하다
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}