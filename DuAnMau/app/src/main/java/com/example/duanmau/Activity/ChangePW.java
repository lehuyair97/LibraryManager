package com.example.duanmau.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.duanmau.R;
import com.example.duanmau.Sqlite.LibrarianDAO;
import com.example.duanmau.model.Librarian;

import java.util.ArrayList;
import java.util.logging.Level;

public class ChangePW extends AppCompatActivity {
    EditText edtOld,edtNew,edtRe;
    TextView tvError;
    Button btn;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(ChangePW.this,R.color.MainColorDuAnMau));
        Toolbar toolbar = findViewById(R.id.toolbarCP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtOld = findViewById(R.id.edtOldPW);
        edtNew = findViewById(R.id.edtNewPW);
        edtRe = findViewById(R.id.edtRetype);
        btn = findViewById(R.id.btnUpdateClick);
        tvError = findViewById(R.id.tvShowError);
        LibrarianDAO librarianDAO = new LibrarianDAO(getApplicationContext());
        ArrayList<Librarian> list = librarianDAO.getData();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOld.getText().toString().isEmpty()||edtNew.getText().toString().isEmpty() || edtRe.getText().toString().isEmpty()){
                    tvError.setText("Vui lòng điền đầy đủ thông tin!");
                }else{
                    tvError.setText("");
                    Intent dataIntent = ChangePW.this.getIntent();

                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getID().equalsIgnoreCase(dataIntent.getStringExtra(MainActivity.ID_LIB))){
                            if(list.get(i).getPASSWORD().equalsIgnoreCase(edtOld.getText().toString()) &&edtNew.getText().toString().equalsIgnoreCase(edtRe.getText().toString())){
                                Librarian librarian = new Librarian(dataIntent.getStringExtra(MainActivity.ID_LIB),list.get(i).getNAME(),edtNew.getText().toString(),list.get(i).getAVATAR());
                                librarianDAO.update(librarian);
                                Log.e("Tag","ID: "+ dataIntent.getStringExtra(MainActivity.ID_LIB) +" PW: "+edtNew.getText().toString());
                                Toast.makeText(ChangePW.this,"Cập nhật mật khẩu thành công!",Toast.LENGTH_SHORT).show();

                            }else{
                                tvError.setText("Thông tin chưa chính xác!");
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
