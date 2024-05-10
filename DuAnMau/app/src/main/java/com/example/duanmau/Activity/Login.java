package com.example.duanmau.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.Sqlite.DbHelper;
import com.example.duanmau.Sqlite.LibrarianDAO;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Librarian;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    TextView tvError;
    EditText edtID, edtPW;
    CheckBox chkRemember;
    Button btnLogin;
    public static final String ID_LIBRARIAN = "ID_Librarian";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this,R.color.MainColorDuAnMau));
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        String error="Sai tên đăng nhập hoặc mật khẩu!";
        tvError = findViewById(R.id.tvError);
        edtID = findViewById(R.id.edtIDLogin);
        edtPW = findViewById(R.id.edtPWLogin);
        chkRemember = findViewById(R.id.chkRemember);
        edtID.setText(sharedPreferences.getString("IDLogin",""));
        edtPW.setText(sharedPreferences.getString("PWLogin",""));
        chkRemember.setChecked(sharedPreferences.getBoolean("checked",false));
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibrarianDAO librarianDAO = new LibrarianDAO(Login.this);
                ArrayList<Librarian> list = librarianDAO.getData();
                for (int i = 0; i < list.size(); i++) {
                    Log.e("Show Message","ID: "+list.get(i).getID());
                    Log.e("Show Message","edtID: "+edtID.getText().toString());
                    if(list.get(i).getID().equalsIgnoreCase(edtID.getText().toString())){
                        if(list.get(i).getPASSWORD().equalsIgnoreCase(edtPW.getText().toString())){
                            tvError.setText("");
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            intent.putExtra(ID_LIBRARIAN,list.get(i).getID());
                            startActivity(intent);
                            if(chkRemember.isChecked()){
                               SharedPreferences.Editor editor = sharedPreferences.edit();
                               editor.putString("IDLogin",edtID.getText().toString().trim());
                               editor.putString("PWLogin",edtPW.getText().toString().trim());
                               editor.putBoolean("checked",true);
                               editor.commit();
                            }else{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("IDLogin");
                                editor.remove("PWLogin");
                                editor.remove("checked");
                                editor.commit();
                            }
                        }
                    }else if(edtID.getText().toString().isEmpty() || edtPW.getText().toString().isEmpty()){
                        tvError.setText("Vui lòng nhập đầy đủ thông tin!");
                    }else{
                        tvError.setText(error);
                    }
                }
            }
        });
    }
}
