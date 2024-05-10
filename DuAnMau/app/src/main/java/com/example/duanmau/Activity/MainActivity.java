package com.example.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.Adapter.AdapterCategory_New_virsion;
import com.example.duanmau.Adapter.Adapter_Books_New_Virsion;
import com.example.duanmau.Adapter.Adapter_CallCard_New_Virsion;
import com.example.duanmau.Adapter.ViewPagerAdapter;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.Sqlite.LibrarianDAO;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Books;
import com.example.duanmau.model.Callcard;
import com.example.duanmau.model.Category;
import com.example.duanmau.model.Librarian;
import com.example.duanmau.model.Members;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ID_LIB = "ID_LIB";
    public static BottomNavigationView bottom_Nav;
    public static ArrayList<Integer> listPicture;
    public static ArrayList<Category> categoryArrayList;
     public static ViewPagerAdapter viewPagerAdapter;
    public static ViewPager viewPager;
    public int REQUEST_CODE_GetImage_Avatar = 11;
    public int REQUEST_CODE_ADD_IMAGE_CATEGORY = 22;
    public int REQUEST_CODE_ADD_IMAGE_BOOk = 33;
    public int REQUEST_CODE_ADD_MEMBER = 44;
    String regexDate ="([0-9]{4})/([0-9]{2})/([0-9]{2})";



    DrawerLayout drawerLayout;
    Toolbar toolbar;
    View view;
    TextView tvUser;
    FragmentManager fragmentManager;
    public static TextView tvTitle;
    ImageView imgAvatar;
    FloatingActionButton btnFloat;
    public static String dateOut ="";
    ImageView imgAddCategory,imgAddBook, imgAvatarMember;


    @SuppressLint({"MissingInflatedId", "WrongThread"})
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.MainColorDuAnMau));
        drawerLayout = findViewById(R.id.Drawer_Layout);
        toolbar = findViewById(R.id.toolbarMain);
        tvTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navtigation_drawer_open,R.string.navtigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_View);
        View header = navigationView.getHeaderView(0);
        TextView tvWelcome = header.findViewById(R.id.tvWelcomeUser);
        imgAvatar = header.findViewById(R.id.imgAvatarNav);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GetImage_Avatar);
            }
        });
        Intent dataIntent = MainActivity.this.getIntent();
        ArrayList<Librarian> listLibrarian = (new LibrarianDAO(getApplicationContext()).getData());
        for (int i = 0; i < listLibrarian.size(); i++) {
            if(listLibrarian.get(i).getID().equalsIgnoreCase(dataIntent.getStringExtra(Login.ID_LIBRARIAN))){
                tvWelcome.setText("Welcome "+listLibrarian.get(i).getNAME()+" !");
                if(listLibrarian.get(i).getID().equalsIgnoreCase("admin")){
                    navigationView.getMenu().findItem(R.id.nav_Add).setVisible(true);
                }
               try {
                   byte[] img = listLibrarian.get(i).getAVATAR();
                   Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
                    imgAvatar.setImageBitmap(bitmap);
               }catch (Exception e){
               }
            }
        }

        bottom_Nav = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            tvTitle.setText("Quản Lý Phiếu Mượn");
                            navigationView.getMenu().findItem(R.id.nav_CallCard).setChecked(true);
                            bottom_Nav.getMenu().findItem(R.id.nav_CallCard1).setChecked(true);
                            btnFloat.show();
                            break;
                        case 1:
                            tvTitle.setText("Thể Loại Sách");
                            navigationView.getMenu().findItem(R.id.nav_Category).setChecked(true);
                            bottom_Nav.getMenu().findItem(R.id.nav_Category1).setChecked(true);
                            btnFloat.show();
                            break;
                        case 2:
                            tvTitle.setText("Kho Sách");
                            navigationView.getMenu().findItem(R.id.nav_Books).setChecked(true);
                            bottom_Nav.getMenu().findItem(R.id.nav_Books1).setChecked(true);
                            btnFloat.show();
                            break;
                        case 3:
                            tvTitle.setText("Quản Lý Thành Viên");
                            navigationView.getMenu().findItem(R.id.nav_Members).setChecked(true);
                            bottom_Nav.getMenu().findItem(R.id.nav_Members1).setChecked(true);
                            btnFloat.show();
                            break;
                        case 4:
                            navigationView.getMenu().findItem(R.id.nav_top).setChecked(true);
                            tvTitle.setText("10 Đầu Sách Hot Nhất");
                            btnFloat.hide();

                            break;
                        case 5:
                            navigationView.getMenu().findItem(R.id.nav_Revenue).setChecked(true);
                            tvTitle.setText("Doanh Thu");
                            btnFloat.hide();
                            break;

                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottom_Nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_CallCard1){
                    viewPager.setCurrentItem(0);
                }else      if(item.getItemId() == R.id.nav_Category1){
                    viewPager.setCurrentItem(1);
                }else      if(item.getItemId() == R.id.nav_Books1){
                    viewPager.setCurrentItem(2);
                }else      if(item.getItemId() == R.id.nav_Members1){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 if(item.getItemId() == R.id.nav_CallCard){
                    viewPager.setCurrentItem(0);
                }else
                    if(item.getItemId() == R.id.nav_Category){
                    viewPager.setCurrentItem(1);
                }else
                    if(item.getItemId() == R.id.nav_Books){
                     viewPager.setCurrentItem(2);
                 }else
                     if(item.getItemId() == R.id.nav_Members){
                     viewPager.setCurrentItem(3);
                 }else
                     if(item.getItemId() == R.id.nav_top){
                     viewPager.setCurrentItem(4);
                 } if(item.getItemId() == R.id.nav_Revenue){
                    viewPager.setCurrentItem(5);
                }
                else
                    if(item.getItemId() == R.id.nav_ChangePW){
                    Intent dataIntent = MainActivity.this.getIntent();
                    String idLib = dataIntent.getStringExtra(Login.ID_LIBRARIAN);
                    Intent intent = new Intent(MainActivity.this,ChangePW.class);
                    intent.putExtra(ID_LIB,idLib);
                    startActivity(intent);
                }
                else
                    if(item.getItemId() == R.id.nav_LogOut){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }else
                    if(item.getItemId() == R.id.nav_Add){
                    startActivity(new Intent(MainActivity.this,AddLibrarian.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        btnFloat = findViewById(R.id.btnFloat);
        btnFloat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BottomSheetDialog bsd = new BottomSheetDialog(viewPager.getContext());
                if(viewPager.getCurrentItem()==0){
                    bsd.setContentView(R.layout.add_callcard_dialog);
                    BooksDAO booksDAO = new BooksDAO(view.getContext());
                    CallCardDAO callCardDAO = new CallCardDAO(view.getContext());
                    Spinner spinnerBooks = bsd.findViewById(R.id.spinnerBookCC);
                    ArrayList<Books> booksArrayList = booksDAO.getData();
                    ArrayList<String> listSpinner = new ArrayList<>();
                    for (int i = 0; i < booksArrayList.size() ; i++) {
                        listSpinner.add(booksArrayList.get(i).getNAME());
                    }
                    ArrayAdapter adt = new ArrayAdapter(getApplicationContext(), R.layout.selected_spinner_custom,listSpinner);
                    adt.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                    spinnerBooks.setAdapter(adt);
                    spinnerBooks.setSelection(0);
                    Spinner spinnerMember = bsd.findViewById(R.id.spinnerMemerCC);
                    ArrayList<Members> MemberArrayList = new MembersDAO(getApplication()).getData();
                    ArrayList<String> listSpinnerMember = new ArrayList<>();
                    for (int i = 0; i < MemberArrayList.size() ; i++) {
                        listSpinnerMember.add(MemberArrayList.get(i).getNAME());
                    }
                    ArrayAdapter adtMember = new ArrayAdapter(getApplicationContext(), R.layout.selected_spinner_custom,listSpinnerMember);
                    adtMember.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                    spinnerMember.setAdapter(adtMember);
                    spinnerMember.setSelection(0);
                    bsd.show();
                    Button btnPickDate = bsd.findViewById(R.id.btnPickDateCC);
                     EditText edtNewDate = bsd.findViewById(R.id.edtNewDateInCC);
                    btnPickDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showPickerDialog (viewPager.getContext(),edtNewDate);
                        }
                    });
                    ImageView btnclose = bsd.findViewById(R.id.btnCancleDialogCallCard);
                    btnclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    Button btnCancle = bsd.findViewById(R.id.btnCancleAddCC);
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    Button btnSave = bsd.findViewById(R.id.btnSaveAddCC);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if(edtNewDate.getText().toString().equalsIgnoreCase("")||edtNewDate.getText().toString().equalsIgnoreCase("")){
                                    Toast.makeText(MainActivity.this, "Vui Lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();

                                }else if(!edtNewDate.getText().toString().matches(regexDate)){
                                    Toast.makeText(MainActivity.this, "Vui lòng nhập đúng định dạng ngày!", Toast.LENGTH_SHORT).show();
                                        }else{
                                    callCardDAO.insert(new Callcard(0,edtNewDate.getText().toString(),dateOut,(spinnerBooks.getSelectedItemPosition()+1),
                                            ( spinnerMember.getSelectedItemPosition()+1),dataIntent.getStringExtra(Login.ID_LIBRARIAN)));
                                    Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                    viewPager.setAdapter(viewPagerAdapter);
                                    viewPager.setCurrentItem(0);

                                }
                            }catch (Exception e){
                                Log.e("Error: ","Error: "+ e.getMessage());
                            }
                        }
                    });
                }
                if(viewPager.getCurrentItem()==1){
                    bsd.setContentView(R.layout.add_category_dialog);
                    BooksDAO booksDAO = new BooksDAO(view.getContext());
                    CallCardDAO callCardDAO = new CallCardDAO(view.getContext());
                    bsd.show();
                    Button btnCancle = bsd.findViewById(R.id.btnCancleAddCategory);
                    Button btnAdd = bsd.findViewById(R.id.btnsaveAddCategory);
                    imgAddCategory = bsd.findViewById(R.id.imgAddPictureCategory);
                    EditText edtCategoryName = bsd.findViewById(R.id.edtAddcategory);
                    ImageView imgCancle = bsd.findViewById(R.id.imgCancleDialogAddCategory);
                    Button btnSave = bsd.findViewById(R.id.btnsaveAddCategory);
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    imgCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    imgAddCategory.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,REQUEST_CODE_ADD_IMAGE_CATEGORY);
                        }
                    });
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(edtCategoryName.getText().toString().equalsIgnoreCase("")){
                                Toast.makeText(MainActivity.this, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                            }else{
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAddCategory.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);
                                byte[] image = byteArrayOutputStream.toByteArray();
                                CategoryDAO categoryDAO = new CategoryDAO(getApplication());
                                categoryDAO.insert(new Category(0,edtCategoryName.getText().toString(),image));
                                Toast.makeText(MainActivity.this, "Add Sucessfully", Toast.LENGTH_SHORT).show();
                                viewPager.setAdapter(viewPagerAdapter);
                                viewPager.setCurrentItem(1);
                            }
                        }
                    });

                }
                if(viewPager.getCurrentItem()==2){
                    bsd.setContentView(R.layout.add_book_dialog);
                    imgAddBook = bsd.findViewById(R.id.imgAddBook);
                    ImageView imgCancle = bsd.findViewById(R.id.imgCancleDialogAddBook);
                    Button btnCancle = bsd.findViewById(R.id.btnCancleAddBook);
                    Button btnSave = bsd.findViewById(R.id.btnSaveAddBook);
                    EditText edtName = bsd.findViewById(R.id.edtNameBook);
                    EditText edtPrice = bsd.findViewById(R.id.edtPriceBook);
                    Spinner spinnerCategory = bsd.findViewById(R.id.spinnerCategoryAddBook);
                    CategoryDAO categoryDAO = new CategoryDAO(getApplication());
                    ArrayList<Category> categoryArrayList1 = categoryDAO.getFullData();
                    ArrayList<String> listSpinner = new ArrayList<>();
                    for (int i = 0; i < categoryArrayList1.size() ; i++) {
                        listSpinner.add(categoryArrayList1.get(i).getNAME());
                    }
                    ArrayAdapter adt = new ArrayAdapter(getApplicationContext(), R.layout.selected_spinner_custom,listSpinner);
                    adt.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                    spinnerCategory.setAdapter(adt);
                    bsd.show();
                    imgAddBook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,REQUEST_CODE_ADD_IMAGE_BOOk);
                        }
                    });
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    imgCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    btnSave.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Boolean flag = true;
                            try{
                                    Integer.parseInt(edtPrice.getText().toString());
                                    flag = true;
                            }catch(Exception e){
                                flag = false;
                                Log.e("Log","Error: "+e.getMessage());
                            }
                            if (edtName.getText().toString().equalsIgnoreCase("") || edtPrice.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(MainActivity.this, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                            }else if(flag == false){
                                Toast.makeText(MainActivity.this, "Vui lòng nhập đúng định dạng tiền !", Toast.LENGTH_SHORT).show();
                            }else{
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAddBook.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100,bos);
                                byte[] image1 = bos.toByteArray();
                                BooksDAO booksDAO = new BooksDAO(getApplication());
                                booksDAO.insert(new Books(0,edtName.getText().toString(),Integer.parseInt
                                        (edtPrice.getText().toString()),(spinnerCategory.getSelectedItemPosition()+1),image1));
                                Toast.makeText(MainActivity.this, "Add Sucessfully", Toast.LENGTH_SHORT).show();
                                viewPager.setAdapter(viewPagerAdapter);
                                viewPager.setCurrentItem(2);
                            }
                        }
                    });

                }
                if(viewPager.getCurrentItem()==3){
                    bsd.setContentView(R.layout.add_member_dialog);
                    Button btnCancle = bsd.findViewById(R.id.btnCancleAddMember);
                    Button btnSave = bsd.findViewById(R.id.btnSaveAddMember);
                    ImageView imgCancle = bsd.findViewById(R.id.imgCancleDialogAddMember);
                    imgAvatarMember = bsd.findViewById(R.id.imgAddMember);
                    EditText edtname = bsd.findViewById(R.id.edtAddNameMember);
                    EditText edtDate = bsd.findViewById(R.id.edtAddAge);
                    bsd.show();
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    imgCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bsd.dismiss();
                        }
                    });
                    imgAvatarMember.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,REQUEST_CODE_ADD_MEMBER);
                        }
                    });
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean flag = true;
                            try{
                                Integer.parseInt(edtDate.getText().toString());
                                flag = true;
                            }catch(Exception e){
                                flag = false;
                                Log.e("Log","Error: "+e.getMessage());
                            }
                            if (edtname.getText().toString().equalsIgnoreCase("") || edtDate.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(MainActivity.this, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                            }else if(flag == false){
                                Toast.makeText(MainActivity.this, "Vui lòng nhập đúng định dạng năm sinh !", Toast.LENGTH_SHORT).show();
                            }else{
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatarMember.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();

                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                                byte [] image = bos.toByteArray();
                                new MembersDAO(getApplication()).insert(new Members(0,edtname.getText().toString(),edtDate.getText().toString(),image));
                                Toast.makeText(MainActivity.this, "Add Sucessfully", Toast.LENGTH_SHORT).show();
                                viewPager.setAdapter(viewPagerAdapter);
                                viewPager.setCurrentItem(3);
                            }


                        }
                    });
                    Toast.makeText(MainActivity.this, "This is Forth Screen", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

//    public void setViewPagerIndex(){
//        viewPagerAdapter.getItem(3);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GetImage_Avatar && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
                Bitmap bitmapDR = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmapDR.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] avt =byteArray.toByteArray();
                Intent dataIntent = MainActivity.this.getIntent();
                ArrayList<Librarian> listLibrarian = (new LibrarianDAO(getApplication()).getData());
                for (int i = 0; i < listLibrarian.size(); i++) {
                    if(listLibrarian.get(i).getID().equalsIgnoreCase(dataIntent.getStringExtra(Login.ID_LIBRARIAN))){
                        Librarian librarian1 = new Librarian(listLibrarian.get(i).getID(),listLibrarian.get(i).getNAME(),listLibrarian.get(i).getPASSWORD(),avt);
                        LibrarianDAO librarianDAO =new LibrarianDAO(getApplicationContext());
                        librarianDAO.update(librarian1);
                        Toast.makeText(this, "Cập nhật Avatar thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if(requestCode == REQUEST_CODE_ADD_IMAGE_CATEGORY && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            try{
                InputStream ip = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                imgAddCategory.setImageBitmap(bitmap);

            }catch (Exception e ){
                Log.e("Tag","Error: "+e.getMessage());
            }
        }
        if(requestCode == REQUEST_CODE_ADD_IMAGE_BOOk && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream ip = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                imgAddBook.setImageBitmap(bitmap);
            }catch (Exception e){
                Log.e("Tag","Error: "+e.getMessage());

            }
        }
        if(requestCode == Adapter_Books_New_Virsion.REQUEST_CODE_FOLDER_Book && resultCode == RESULT_OK && data!= null) {
            Uri uri = data.getData();
            try {
                InputStream ip = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                Adapter_Books_New_Virsion.imgUpdate.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("Tag", "Error: " + e.getMessage());
            }
        }
        if(requestCode == AdapterCategory_New_virsion.REQUEST_CODE_FOLDER_CATEGORY && resultCode == RESULT_OK && data!= null) {
            Uri uri = data.getData();
            try {
                InputStream ip = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                AdapterCategory_New_virsion.imgUpdate.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("Tag", "Error: " + e.getMessage());
            }
        }
        if(requestCode == REQUEST_CODE_ADD_MEMBER && resultCode == RESULT_OK && data!= null) {
            Uri uri = data.getData();
            try {
                InputStream ip = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ip);
                imgAvatarMember.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("Tag", "Error: " + e.getMessage());
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    public void showPickerDialog(Context context,EditText edt) {
        Calendar calendar = Calendar.getInstance();
        int day =  calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DatePickerDialog pickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                edt.setText(simpleDateFormat.format(calendar.getTime()));
                                calendar.set(i,i1+1,i2);
                dateOut = simpleDateFormat.format(calendar.getTime());
                Toast.makeText(MainActivity.this, "value: "+dateOut, Toast.LENGTH_SHORT).show();

            }
        },year,month,day);
        pickerDialog.show();

    }
    public static  void updateImage(BooksDAO booksDAO, CategoryDAO categoryDAO,MembersDAO membersDAO, Resources resources){
        ArrayList<Integer> imgBook =new ArrayList<>();
        imgBook.add(R.drawable.sach1);
        imgBook.add(R.drawable.sach2);
        imgBook.add(R.drawable.sach3);
        imgBook.add(R.drawable.sach4);
        imgBook.add(R.drawable.sach5);
        imgBook.add(R.drawable.sach6);
        imgBook.add(R.drawable.sach7);
        imgBook.add(R.drawable.sach8);
        imgBook.add(R.drawable.sach9);
        imgBook.add(R.drawable.sach10);
        imgBook.add(R.drawable.sach11);
        imgBook.add(R.drawable.sach12);
        imgBook.add(R.drawable.sach13);
        imgBook.add(R.drawable.sach14);
        imgBook.add(R.drawable.sach15);
        imgBook.add(R.drawable.sach16);
        imgBook.add(R.drawable.sach17);
        imgBook.add(R.drawable.sach18);
        for (int i = 0; i < imgBook.size() ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(resources, imgBook.get(i));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
            byte[] image = byteArray.toByteArray();
            booksDAO.updateImage(i+1,image);
        }
        ArrayList<Integer> img2= new ArrayList<>();
        img2.add(R.drawable.kinhte);
        img2.add(R.drawable.giaoduc);
        img2.add(R.drawable.lichsu);
        img2.add(R.drawable.dautu);
        img2.add(R.drawable.tieuthuyet);
        img2.add(R.drawable.selfhelp);
        ArrayList<Category> list1 = categoryDAO.getFullData();
        for (int i = 0; i < img2.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(resources, img2.get(i));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] image1 = byteArray.toByteArray();
            categoryDAO.updateImage(i + 1, image1);
        }
        ArrayList<Integer> imgListMember = new ArrayList<>();
        imgListMember = new ArrayList<>();
        imgListMember.add(new Integer(R.drawable.member1));
        imgListMember.add(new Integer(R.drawable.member7));
        imgListMember.add(new Integer(R.drawable.member3));
        imgListMember.add(new Integer(R.drawable.member8));
        imgListMember.add(new Integer(R.drawable.member9));
        imgListMember.add(new Integer(R.drawable.member10));
        imgListMember.add(new Integer(R.drawable.member4));
        imgListMember.add(new Integer(R.drawable.member11));
        imgListMember.add(new Integer(R.drawable.member5));
        imgListMember.add(new Integer(R.drawable.member12));

        for (int i = 0; i < imgListMember.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(resources,imgListMember.get(i));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            byte[] image = bos.toByteArray();
            membersDAO.updateImage(i+1,image);
        }
    }
    public void checkData(){
                CategoryDAO categoryDao = new CategoryDAO(MainActivity.this);
        ArrayList<Category> category = categoryDao.getFullData();
        for (int i = 0; i < category.size() ; i++) {
            Log.e("Category","ID: "+ category.get(i).getID() +" Category:  "+category.get(i).getNAME() + "img" + category.get(i).getIMG());
        }
        BooksDAO booksDAO = new BooksDAO(getApplicationContext());
        ArrayList<Books> book = booksDAO.getData();
        for (int i = 0; i < book.size() ; i++) {
            Log.e("Book","IID: "+ book.get(i).getID()+" Book:  "+book.get(i).getNAME()+ "ID CAtegory " +book.get(i).getIDCATEGORY() ) ;
        }
//
        MembersDAO membersBooksDAO = new MembersDAO(MainActivity.this);
        ArrayList<Members> member = membersBooksDAO.getData();
        for (int i = 0; i < member.size() ; i++) {
            Log.e("Member","ID: "+ member.get(i).getID()+" Member:  "+member.get(i).getNAME());
        }

        LibrarianDAO Libariandao = new LibrarianDAO(MainActivity.this);
        ArrayList<Librarian> librarian = Libariandao.getData();
        for (int i = 0; i < librarian.size() ; i++) {
            Log.e("Librarian","ID: "+ librarian.get(i).getID()+" Librarin:  "+librarian.get(i).getNAME());
        }

        CallCardDAO callCardDAO = new CallCardDAO(MainActivity.this);
        ArrayList<Callcard> callcards = callCardDAO.getData();
        for (int i = 0; i < callcards.size() ; i++) {
            Log.e("Librarian","ID: "+ callcards.get(i).getID()+" CallCard:  "+ callcards.get(i).getIDBOOKS());
        }
//
    }
}
