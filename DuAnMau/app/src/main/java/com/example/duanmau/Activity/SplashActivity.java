package com.example.duanmau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.Sqlite.DbHelper;
import com.example.duanmau.Sqlite.MembersDAO;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.MainColorDuAnMau));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                MainActivity.updateImage(new BooksDAO(getApplicationContext()),new CategoryDAO(getApplicationContext()),
                        new MembersDAO(getApplicationContext()),getResources());
                Intent i = new Intent(SplashActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        },1500);

    }
}
