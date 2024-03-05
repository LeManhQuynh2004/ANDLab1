package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassWordScreen extends AppCompatActivity {

    private TextView ed_email;
    private Button bt_forget;
    private TextView tv_send_login;
    private FirebaseAuth mAuth;
    private void initView(){
        ed_email = findViewById(R.id.ed_email);
        bt_forget = findViewById(R.id.bt_forger);
        mAuth = FirebaseAuth.getInstance();
        tv_send_login = findViewById(R.id.tv_send_login);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word_screen);
        initView();
        bt_forget.setOnClickListener(v -> {
            String email = ed_email.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Vui lòng không bỏ trống email", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgetPassWordScreen.this, "Vui lòng kiểm tra lại email để cập nhất password", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ForgetPassWordScreen.this, "Lỗi gửi Email", Toast.LENGTH_SHORT).show();
                    }
            });
        });
        tv_send_login.setOnClickListener(v -> {
            finish();
        });
    }
}