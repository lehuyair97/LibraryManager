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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Members;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Adapter_Member_New_Virsion extends RecyclerView.Adapter<Adapter_Member_New_Virsion.MemberHolder>{
    Activity activity;


    public Adapter_Member_New_Virsion(Activity activity) {
        this.activity = activity;
    }
    Context context;
    ArrayList<Members> membersArrayList;
    ArrayList<Integer> imglist;
    ImageView imgUpdate;
    public static int REQUEST_CODE_FOLDER_MEMBER = 354;

    public Adapter_Member_New_Virsion(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Members> list){
        this.membersArrayList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item_layout,parent,false);
        return new MemberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberHolder holder, int position) {
        Members members = membersArrayList.get(position);
        if(members == null){
            return;
        }
        holder.tvID.setText("no."+members.getID());
        holder.tvNAME.setText("Họ và Tên: "+members.getNAME());
        holder.tvDATE.setText("Năm sinhh: "+members.getDATE());

        try{
            byte[] image = members.getIMAGE();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            holder.imgAvatar.setImageBitmap(bitmap);

        }catch (Exception e){
            Log.e("Error", "Error: "+e.getMessage());
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
                    Log.e("tag","IDMember: " +members.getID());
                    BottomSheetDialog dialogUpdate = new BottomSheetDialog(context);
                    dialogUpdate.setContentView(R.layout.update_member_dialog);
                    Button btnSave = dialogUpdate.findViewById(R.id.btnSaveUpdateMember);
                    Button btnCancle =  dialogUpdate.findViewById(R.id.btnCancleUpdateMember);
                    ImageView imgCancleDialog = dialogUpdate.findViewById(R.id.btnCancleDialogMember);
                    imgUpdate = dialogUpdate.findViewById(R.id.imgUpdateMember);
                    imgUpdate.setImageDrawable(holder.imgAvatar.getDrawable());
                    EditText edtName = dialogUpdate.findViewById(R.id.edtNameMember);
                    EditText edtDate = dialogUpdate.findViewById(R.id.edtDateMember);
                    edtName.setText(members.getNAME());
                    edtDate.setText(members.getDATE()+"");
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
                            origin.startActivityForResult(intent, REQUEST_CODE_FOLDER_MEMBER);

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
                            if(edtName.getText().toString().equalsIgnoreCase("")||edtDate.getText().toString().equalsIgnoreCase("")){
                                Toast.makeText(context, "Vui lòng không để trống !", Toast.LENGTH_SHORT).show();
                            }else if(flag == false){
                                Toast.makeText(context, "Vui lòng nhập đúng định dạng năm sinh  !", Toast.LENGTH_SHORT).show();
                            }else{
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUpdate.getDrawable();
                                Bitmap bitmap = bitmapDrawable.getBitmap();
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                                byte[] image = bos.toByteArray();
                                MembersDAO membersDAO = new MembersDAO(context);
                                membersDAO.update(new Members(members.getID(), edtName.getText().toString(),edtDate.getText().toString(),image));
//                            MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                MainActivity.viewPager.setCurrentItem(3);
                                Toast.makeText(context.getApplicationContext(), "Update Successfully",Toast.LENGTH_SHORT).show();
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
                            int  idMember = members.getID();
                            CallCardDAO callCardDAO = new CallCardDAO(context.getApplicationContext());
                            callCardDAO.deleteByMemberID(String.valueOf(idMember));
                            MembersDAO membersDAO = new MembersDAO(context.getApplicationContext());
                            membersDAO.delete(String.valueOf(idMember));
                            MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                            MainActivity.viewPager.setCurrentItem(3);
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
        return membersArrayList.size();
    }

    public class MemberHolder extends RecyclerView.ViewHolder {
    TextView tvID, tvNAME, tvDATE;
    ImageView imgAvatar;
    RelativeLayout rl;
        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvIDMember);
            tvNAME = itemView.findViewById(R.id.tvNameMember);
            tvDATE = itemView.findViewById(R.id.tvDateMember);
            imgAvatar = itemView.findViewById(R.id.imgAvatarMember);
            rl = itemView.findViewById(R.id.rlMember);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE_FOLDER_MEMBER && resultCode == RESULT_OK ){
//            Uri uri= data.getData();
//            try {
//                InputStream ip =getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(ip);
//                img_add.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
////
////        }
//    }
}
