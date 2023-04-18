package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignUpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginbutton).setOnClickListener(onClickListener); // 회원가입 버튼
        findViewById(R.id.gologin).setOnClickListener(onClickListener); // 로그인 페이지로 이동
    }

    @Override
    public void onBackPressed() { // 로그아웃하고 뒤로가기 누를떄 앱을 끔
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void signUp() {
        String email = ((EditText) findViewById(R.id.emailcheck)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.passwordcheck)).getText().toString();
        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //Log.d(TAG,"success");
                                    startToast("회원가입을 성공했습니다");
                                    FirebaseUser user = mAuth.getCurrentUser();


                                } else {
                                    //Log.w(TAG,"fail",task.getException());
                                    if(task.getException() != null){ // null 값을 입력시
                                        startToast(task.getException().toString());
                                    }

                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지 않습니다");
            }
        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }


    }

    View.OnClickListener onClickListener = new View.OnClickListener() { // 버튼 눌렀을떄 작동되는지 test
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginbutton:
                    signUp();
                    Log.e("test","test");
                    break;
                case R.id.gologin:
                    myStartActivity();
                    break;
            }
        }
    };

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}