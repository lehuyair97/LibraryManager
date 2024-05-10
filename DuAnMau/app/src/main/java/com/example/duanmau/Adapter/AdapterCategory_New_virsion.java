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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Books;
import com.example.duanmau.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterCategory_New_virsion extends RecyclerView.Adapter<AdapterCategory_New_virsion.CategoryViewHolder>  {
    Context context;
    MainActivity mainActivity;
    ArrayList<Category> categoryArrayList;
    public static int REQUEST_CODE_FOLDER_CATEGORY =11;
    public static ImageView imgUpdate;

    public AdapterCategory_New_virsion(Context context, ArrayList<Category> list) {
        this.context = context;
        this.categoryArrayList = list;
    }
    public void setData(ArrayList<Category> list){
        this.categoryArrayList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_of_book_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category  category= categoryArrayList.get(position);
        if(category == null){
            return;
        }
        holder.tvID.setText(category.getID()+"");
        holder.tvName.setText(category.getNAME());
        try{
            byte[] img = category.getIMG();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            holder.imgCategory.setImageBitmap(bitmap);
        }catch (Exception e){
            Log.e("tag","Error: "+e.getMessage());
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
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
                        dialogUpdate.setContentView(R.layout.update_category_dialog);
                        Button btnSave = dialogUpdate.findViewById(R.id.btnSaveUpdateCategory);
                        Button btnCancle =  dialogUpdate.findViewById(R.id.btnCancleUpdateCategory);
                        ImageView imgCancleDialog = dialogUpdate.findViewById(R.id.btnCancleDialogUpdate);
                        imgUpdate = dialogUpdate.findViewById(R.id.imgUpdateCategory);
                        EditText edtName = dialogUpdate.findViewById(R.id.edtUpdateCategory);
                        edtName.setText(category.getNAME());
                        if(imgUpdate == null){
                            imgUpdate.setImageResource(R.id.imgUpdateCategory);
                        }else{
                            imgUpdate.setImageDrawable(holder.imgCategory.getDrawable());
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
                                origin.startActivityForResult(intent, REQUEST_CODE_FOLDER_CATEGORY);

                            }
                        });
                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(edtName.getText().toString().equalsIgnoreCase("")){
                                    Toast.makeText(context, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                                }else{
                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUpdate.getDrawable();
                                    Bitmap bitmap = bitmapDrawable.getBitmap();
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                                    byte[] image = bos.toByteArray();
                                    CategoryDAO categoryDAO = new CategoryDAO(context);
                                    categoryDAO.update(new Category(category.getID(),edtName.getText().toString(),image));
                                    Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show();
                                    MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                    MainActivity.viewPager.setCurrentItem(1);
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
                                int IDCategory = category.getID();
                                BooksDAO booksDAO = new BooksDAO(context.getApplicationContext());
                                CategoryDAO categoryDAO = new CategoryDAO(context.getApplicationContext());
                                CallCardDAO callCardDAO = new CallCardDAO(context.getApplicationContext());
                                AdapterCategory_New_virsion adtCC = new AdapterCategory_New_virsion(context.getApplicationContext(),categoryArrayList);
                                callCardDAO.deleteCallCardByIDCategory(IDCategory);
                                categoryDAO.delete(String.valueOf(IDCategory));
                                callCardDAO.deleteCallCardByIDCategory(IDCategory);
                                MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                MainActivity.viewPager.setCurrentItem(1);
                                BooksDAO booksDAO1 = new BooksDAO(context.getApplicationContext());
                                booksDAO1.deleteBooksByIDCategory(IDCategory);
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
        if(categoryArrayList.size()== 0){
        return 0;

        }
        return  categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
      ImageView imgCategory;
      TextView tvID, tvName;
      CardView cv;
      public CategoryViewHolder(@NonNull View itemView) {
          super(itemView);
        imgCategory = itemView.findViewById(R.id.imgCategory);
        tvID= itemView.findViewById(R.id.tvIDCategory);
        tvName = itemView.findViewById(R.id.tvNameCategory);
        cv = itemView.findViewById(R.id.cvCategory);

      }

  }


}
