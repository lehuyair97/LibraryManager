package com.example.duanmau.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmau.R;
import com.example.duanmau.Sqlite.LibrarianDAO;
import com.example.duanmau.model.Librarian;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddLibrarian extends AppCompatActivity {
    EditText edtName, edtID, edtPW, edtConfrimPW;
    Button btnAdd, btnCancle;
    ImageView img_add;
    private static final int REQUEST_ADD_IMAGE_CODE = 3474;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_librarian_screen);
        edtID = findViewById(R.id.edtIDAdd);
        edtName = findViewById(R.id.edtAddName);
        edtPW = findViewById(R.id.edtPW);
        edtConfrimPW = findViewById(R.id.edtconfirmPW);
        btnAdd = findViewById(R.id.btnSaveAdd);
        img_add = findViewById(R.id.add_img);
        img_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_ADD_IMAGE_CODE);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });

        btnCancle = findViewById(R.id.btnCancleAdd);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddLibrarian.this,MainActivity.class));
            }

        });
    }

    public void process(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_add.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        byte[] image = byteArray.toByteArray();
        LibrarianDAO librarianDAO = new LibrarianDAO(getApplicationContext());
        ArrayList<Librarian> list = librarianDAO.getData();
        if(edtName.getText().toString().equalsIgnoreCase("")||
                edtID.getText().toString().equalsIgnoreCase("")||
                edtPW.getText().toString().equalsIgnoreCase("")||
          edtConfrimPW.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Vui lòng không để trống thông tin!", Toast.LENGTH_SHORT).show();
        }else
            {
                for (int i = 0; i < list.size() ; i++) {
                    if(edtID.getText().toString().equalsIgnoreCase(list.get(i).getID())){
                        Toast.makeText(this, "Tài Khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!edtPW.getText().toString().equalsIgnoreCase(edtConfrimPW.getText().toString())){
                            Toast.makeText(this, "Mật khẩu  xác nhận không đúng !", Toast.LENGTH_SHORT).show();
                        }else{
                            Librarian librarian = new Librarian(edtID.getText().toString(),edtName.getText().toString(),edtPW.getText().toString(),image);
                            librarianDAO.insert(librarian);
                            Toast.makeText(this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();

                        }

                        }
                    }
                }

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD_IMAGE_CODE && resultCode == RESULT_OK ){
            Uri  uri= data.getData();
            try {
                InputStream ip =getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                img_add.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
