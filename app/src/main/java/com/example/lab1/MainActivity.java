package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.bt_logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn thoát ứng dụng không ?");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        redirectToLoginScreen();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });
    }

    private void redirectToLoginScreen() {
        startActivity(new Intent(MainActivity.this, LoginScreen.class));
        finish();
    }
}