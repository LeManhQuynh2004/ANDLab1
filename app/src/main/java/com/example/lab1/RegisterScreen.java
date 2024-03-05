package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterScreen extends AppCompatActivity {

    private EditText ed_email, ed_password,ed_rePassword;

    private FirebaseAuth mAuth;

    private TextView tv_send_login;

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
        setContentView(R.layout.activity_register_screen);
        initView();

        findViewById(R.id.bt_register).setOnClickListener(v -> {
            String email = ed_email.getText().toString().trim();
            String password = ed_password.getText().toString().trim();
            String rePassword = ed_rePassword.getText().toString().trim();
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)){
                Toast.makeText(this, "Vui lòng không bỏ trống dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password.equalsIgnoreCase(rePassword)){
                Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterScreen.this, "Register SuccessFully", Toast.LENGTH_SHORT).show();
                                finish();
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                Toast.makeText(RegisterScreen.this, "Register Not SuccessFully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });//Method Register
        tv_send_login.setOnClickListener(v -> {
            finish();
        });
    }
    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_rePassword = findViewById(R.id.ed_repassword);
        tv_send_login = findViewById(R.id.tv_send_login);
    }
}