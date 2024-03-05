package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginWithPhoneNumber extends AppCompatActivity {

    private static final String TAG = "LoginWithPhoneNumber";

    private EditText ed_phoneNumber, ed_otp;
    private Button sendOTP, bt_login;

    private String mVerificationId;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FirebaseAuth mAuth;

    private void initView() {
        ed_phoneNumber = findViewById(R.id.ed_phone);
        ed_otp = findViewById(R.id.ed_otp);
        sendOTP = findViewById(R.id.bt_sendOtp);
        bt_login = findViewById(R.id.bt_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone_number);
        initView();

        sendOTP.setOnClickListener(v -> {
            String phoneNumber = ed_phoneNumber.getText().toString().trim();
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+84"+phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(LoginWithPhoneNumber.this, "Send Otp thành công", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Log.e(TAG, "onVerificationFailed: " + e.getMessage());
                                    Toast.makeText(LoginWithPhoneNumber.this, "Send Otp thất bại", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    mVerificationId = s;
                                    Toast.makeText(LoginWithPhoneNumber.this, "Send thành công", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        });


        bt_login.setOnClickListener(v -> {
            String otp = ed_otp.getText().toString().trim();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
            signInWithPhoneAuthCredential(credential);
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginWithPhoneNumber.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginWithPhoneNumber.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginWithPhoneNumber.this, "Mã xác nhận không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}