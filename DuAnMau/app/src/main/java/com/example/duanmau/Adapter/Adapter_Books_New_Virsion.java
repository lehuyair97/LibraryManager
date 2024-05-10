package com.example.duanmau.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.model.Books;
import com.example.duanmau.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Adapter_Books_New_Virsion extends RecyclerView.Adapter<Adapter_Books_New_Virsion.BooksHolder>
{
    Context context;
    ArrayList<Books> listbooks;
    public static ImageView imgUpdate;
    public static final int REQUEST_CODE_FOLDER_Book =101;

    public Adapter_Books_New_Virsion(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Books> list){
        this.listbooks = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_new_virsion,parent,false);
        return new BooksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksHolder holder, int position) {
        Books books = listbooks.get(position);
        BooksDAO booksDAO = new BooksDAO(context.getApplicationContext());
        CallCardDAO callCardDAO = new CallCardDAO(context.getApplicationContext());
        if(books == null){
            return;
        }
        CategoryDAO categoryDAO = new CategoryDAO(context);
        String NameCategory =  categoryDAO.getNameCategoryByID(books.getIDCATEGORY());
        holder.tvID.setText("Mã sách: "+books.getID());
        holder.tvNAME.setText("Tên sách: "+ books.getNAME());
        holder.tvCategory.setText("Thể loại: "+NameCategory);
        holder.tvPRICE.setText("Giá sách: "+ String.valueOf(books.getPRICE()));
        try{
            byte[] img = books.getIMAGE();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            holder.img.setImageBitmap(bitmap);
        }catch (Exception e){
            holder.img.setImageResource(R.drawable.add_image);

            Log.e("Error", "error "+ e.getMessage());
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(R.layout.bottom_sheet_dialog);
                LinearLayout fix = dialog.findViewById(R.id.lnFix);
                LinearLayout del = dialog.findViewById(R.id.lnDelete);
                ImageView imgCancle = dialog.findViewById(R.id.cancelButton);
                dialog.show();
                fix.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog dialogUpdate = new BottomSheetDialog(context);
                        dialogUpdate.setContentView(R.layout.update_book_dialog);
                        Button btnSave = dialogUpdate.findViewById(R.id.btnSaveUpdateBook);
                        Button btnCancle =  dialogUpdate.findViewById(R.id.btnCancleUpdateBook);
                        ImageView imgCancleDialog = dialogUpdate.findViewById(R.id.btnCancleDialogBook);
                        imgUpdate = dialogUpdate.findViewById(R.id.imgUpdateBook);
                        EditText edtName = dialogUpdate.findViewById(R.id.edtNewName);
                        EditText edtPrice = dialogUpdate.findViewById(R.id.edtNewPrice);
                        edtName.setText(books.getNAME());
                        edtPrice.setText(books.getPRICE()+"");
                        Spinner spinner = dialogUpdate.findViewById(R.id.spinner);
                        ArrayList<Category> categories = categoryDAO.getFullData();
                        ArrayList<String> listSpinner = new ArrayList<>();
                        for (int i = 0; i < categories.size() ; i++) {
                            listSpinner.add(categories.get(i).getNAME());
                        }
                        ArrayAdapter adt = new ArrayAdapter(context.getApplicationContext(), R.layout.selected_spinner_custom,listSpinner);
                        adt.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                        spinner.setAdapter(adt);
                        try{
                            categories.get(books.getIDCATEGORY()).getNAME();
                        }catch (Exception e){
                            Log.e("Error", "Error: "+e.getMessage());
                        }
                        if(imgUpdate == null){
                            imgUpdate.setImageResource(R.drawable.add_image);
                        }else{
                            imgUpdate.setImageDrawable(holder.img.getDrawable());
                        }


                        dialogUpdate.show();
                        btnCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogUpdate.dismiss();
                            }
                        });
                        imgCancleDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogUpdate.dismiss();
                                dialog.dismiss();
                            }
                        });
                        imgUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                Activity origin = (Activity)context;
                                origin.startActivityForResult(intent, REQUEST_CODE_FOLDER_Book);
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
                                if(edtName.getText().toString().equalsIgnoreCase("")||edtPrice.getText().toString().equalsIgnoreCase("")){
                                    Toast.makeText(context, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                                }else if(flag == false){
                                    Toast.makeText(context, "Vui lòng nhập đúng định dạng tiền  !", Toast.LENGTH_SHORT).show();
                                }else{
                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUpdate.getDrawable();
                                    Bitmap bitmap = bitmapDrawable.getBitmap();
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                                    byte[] image = bos.toByteArray();
                                    booksDAO.update(new Books(books.getID(),edtName.getText().toString(),Integer.parseInt
                                            (edtPrice.getText().toString()),(spinner.getSelectedItemPosition()+1),image));
                                    Toast.makeText(context.getApplicationContext(), "Update Successfully",Toast.LENGTH_SHORT).show();
                                    MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                    MainActivity.viewPager.setCurrentItem(2);
                                }

                            }
                        });
                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog1 = new Dialog(context);
                        dialog1.setContentView(R.layout.delete_dialog);
                        dialog1.show();
                        Button btnYes = dialog1.findViewById(R.id.btnYes);
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int IDBook = books.getID();
                                Adapter_Books_New_Virsion adtCC = new Adapter_Books_New_Virsion(context.getApplicationContext());
                                callCardDAO.deleteByBookID(String.valueOf(IDBook));
                                booksDAO.delete(String.valueOf(IDBook));
                                MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                MainActivity.viewPager.setCurrentItem(2);
                                Toast.makeText(context.getApplicationContext(), "Delete Successfully",Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                            }
                        });
                        Button btnNo = dialog1.findViewById(R.id.btnNo);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });

                    }
                });
                imgCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return listbooks.size();
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Log.d("MyAdapter", "onActivityResult");
//        if(requestCode == REQUEST_CODE_FOLDER_Book && resultCode == resultCode){
//            Uri uri = data.getData();
//            try {
//                InputStream ip = ((Activity) context).getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(ip);
//                imgUpdate.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        onActivityResult(requestCode, resultCode, data);
//
//    }

    public class BooksHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvID, tvNAME,tvCategory, tvPRICE;
        RelativeLayout rl;

        public BooksHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgBook);
            tvID = itemView.findViewById(R.id.tvIDBook);
            tvNAME = itemView.findViewById(R.id.tvNameBook1);
            tvCategory = itemView.findViewById(R.id.tvCategoryBook);
            tvPRICE = itemView.findViewById(R.id.tvPriceBook1);
            rl = itemView.findViewById(R.id.rlBook);
        }
    }
}
