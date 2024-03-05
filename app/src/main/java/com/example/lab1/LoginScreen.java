package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private EditText ed_email, ed_password;
    private TextView tv_send_register,tv_forgetPass;
    private FirebaseAuth mAuth;

    private Button bt_login_phoneNumber;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
           currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initView();
        findViewById(R.id.bt_login).setOnClickListener(v -> {
            String email = ed_email.getText().toString().trim();
            String password = ed_password.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng không bỏ trống trường dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginScreen.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                startActivity(intent);
                                clearForm();
                            } else {
                                Toast.makeText(LoginScreen.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });//Method Login
        tv_send_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
        });
        tv_forgetPass.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, ForgetPassWordScreen.class);
            startActivity(intent);
        });
        bt_login_phoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, LoginWithPhoneNumber.class);
            startActivity(intent);
        });
    }

    private void initView() {
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        tv_send_register = findViewById(R.id.tv_send_register);
        tv_forgetPass = findViewById(R.id.tv_forgotPassWord);
        bt_login_phoneNumber = findViewById(R.id.bt_login_phoneNumber);
    }
    private void clearForm(){
        ed_email.setText("");
        ed_password.setText("");
    }
}