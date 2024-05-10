package com.example.duanmau.Adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Activity.Login;
import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Books;
import com.example.duanmau.model.Callcard;
import com.example.duanmau.model.Members;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Adapter_CallCard_New_Virsion extends RecyclerView.Adapter<Adapter_CallCard_New_Virsion.CallCardHolder> {
    Context context;
    ArrayList<Callcard> callcardArrayList;
    Spinner spinnerBooks, spinnerMember;
    ArrayAdapter adt;
    ArrayAdapter adtMember;
    ArrayList<Books> BooksList;
    ArrayList<Members> member;
    BooksDAO booksDAO;
    ArrayList<Members> members ;
    ArrayList<Books> books;
    MembersDAO membersDAO;
    EditText edtNewDate;
    String regexDate ="([0-9]{4})/([0-9]{2})/([0-9]{2})";


    public Adapter_CallCard_New_Virsion(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Callcard> list){
        this.callcardArrayList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CallCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_card_item,parent,false);
        return new CallCardHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CallCardHolder holder, @SuppressLint("RecyclerView") int position) {

        Callcard callcard = callcardArrayList.get(position);
        if(callcard == null){
            return;
        }
        membersDAO = new MembersDAO(context);
        members = membersDAO.getData();
         booksDAO= new BooksDAO(context);
        books = booksDAO.getData();
        try{
            holder.tvID.setText("No." + callcard.getID());
            Log.e("Log","Message: "+callcard.getIDMEMBERS());
            holder.tvnameMember.setText("Thành viên: " +  members.get(callcard.getIDMEMBERS()-1).getNAME());
            holder.tvNameBook.setText("Tên sách: " + books.get(callcard.getIDBOOKS()-1).getNAME());
            holder.tvPrice.setText("Giá thuê: " + (books.get(callcard.getIDBOOKS()-1).getPRICE())/10);
            holder.tvdateIn.setText("Ngày thuê: " + callcard.getDATEIN());
        }catch (Exception e){
            Log.e("Error", "Error: " +e.getMessage());
        }
        String currentDateString = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        String dateOut = callcard.getDATEOUT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date dateOut1 = sdf.parse(dateOut);
            Date currentDate = sdf.parse(currentDateString);

            if(dateOut1.compareTo(currentDate)>0){
                holder.tvStatus.setText("Statusi: Chưa trả");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context,R.color.green));
                Log.e("Tag", "Ngày hiện tại bé hơn " );
            }else{
                holder.tvStatus.setText("Status: Đã trả");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context,R.color.MainColorDuAnMau));

            }

        } catch (Exception e) {
            e.getMessage();
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallCardDAO callCardDAO = new CallCardDAO(context.getApplicationContext());
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
                        dialogUpdate.setContentView(R.layout.update_callcard_dialog);
                        Button btnSave = dialogUpdate.findViewById(R.id.btnSaveUpdateCallCard);
                        Button btnCancle =  dialogUpdate.findViewById(R.id.btnCancleUpdateCallCard);
                        ImageView imgCancleDialog = dialogUpdate.findViewById(R.id.btnCancleDialogCallCard);
                        edtNewDate  = dialogUpdate.findViewById(R.id.edtNewDateIn);
                        edtNewDate.setText(callcard.getDATEIN());
                        Button btnPickDate =dialogUpdate.findViewById(R.id.btnPickDate);
                        edtNewDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        btnPickDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showPickerDialog();

                            }
                        });
                        spinnerBooks = dialogUpdate.findViewById(R.id.spinnerBook);
                        BooksList = booksDAO.getData();
                        ArrayList<String> listSpinner = new ArrayList<>();
                        for (int i = 0; i < BooksList.size() ; i++) {
                            listSpinner.add(BooksList.get(i).getNAME());
                        }
                        adt = new ArrayAdapter(context.getApplicationContext(), R.layout.selected_spinner_custom,listSpinner);
                        adt.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                        spinnerBooks.setAdapter(adt);
                        Log.e("Log", "Message: "+callcard.getIDBOOKS());
                        spinnerBooks.setSelection(callcard.getIDBOOKS()-1);
                        member = membersDAO.getData();
                        spinnerMember = dialogUpdate.findViewById(R.id.spinnerMember);
                        ArrayList<String> listSpinnerMember = new ArrayList<>();
                        for (int i = 0; i < member.size() ; i++) {
                            listSpinnerMember.add(member.get(i).getNAME());
                        }
                        adtMember = new ArrayAdapter(context.getApplicationContext(), R.layout.selected_spinner_custom,listSpinnerMember);
                        adtMember.setDropDownViewResource(R.layout.dropdown_spinner_custom);
                        spinnerMember.setAdapter(adtMember);
                        spinnerMember.setSelection(callcard.getIDMEMBERS()-1);
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

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    if(edtNewDate.getText().toString().equalsIgnoreCase("")||edtNewDate.getText().toString().equalsIgnoreCase("")){
                                        Toast.makeText(context, "Vui Lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();

                                    }else if(!edtNewDate.getText().toString().matches(regexDate)){
                                        Toast.makeText(context, "Vui lòng nhập đúng định dạng ngày!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        callCardDAO.update(new Callcard(callcard.getID(),edtNewDate.getText().toString(),dateOut.toString(),
                                                ( spinnerBooks.getSelectedItemPosition()+1) ,(spinnerMember.getSelectedItemPosition()+1), Login.ID_LIBRARIAN));
                                        Toast.makeText(context, "Update Sucessfully", Toast.LENGTH_SHORT).show();
                                        MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                        MainActivity.viewPager.setCurrentItem(0);

                                    }
                                }catch (Exception e){
                                    Log.e("Error: ","Error: "+ e.getMessage());
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
                                callCardDAO.delete(String.valueOf(callcard.getID()));
                                MainActivity.viewPager.setAdapter(MainActivity.viewPagerAdapter);
                                MainActivity.viewPager.setCurrentItem(0);
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
        return callcardArrayList.size();
    }

    public void showPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int day =  calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                edtNewDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        pickerDialog.show();
    }



    public class CallCardHolder extends RecyclerView.ViewHolder {
    TextView tvID, tvNameBook, tvnameMember, tvPrice, tvdateIn, tvStatus;
    RelativeLayout rl;
        public CallCardHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvIDCallCard);
            tvNameBook = itemView.findViewById(R.id.tvNameBookCC);
            tvnameMember = itemView.findViewById(R.id.tvNameMemberCC);
            tvdateIn = itemView.findViewById(R.id.tvDateInCC);
            tvPrice = itemView.findViewById(R.id.tvPriceCC);
            tvStatus = itemView.findViewById(R.id.tvStatusCC);
            rl = itemView.findViewById(R.id.rlCallCard);

        }
    }



}


