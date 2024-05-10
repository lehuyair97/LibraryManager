package com.example.duanmau.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.R;
import com.example.duanmau.Sqlite.StatisticsDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Revenue_Fragment extends Fragment {
    TextView tvIn, tvOut, tvDataselected, tvRemaining, tvTotal;
    EditText edtIn, edtOut;
    Button btnShow;
    String regexDate ="([0-9]{4})/([0-9]{2})/([0-9]{2})";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.revenue_screen,container,false);
        PieChart pieChart = view.findViewById(R.id.chart);
        tvIn = view.findViewById(R.id.tvIn);
        tvOut =view.findViewById(R.id.tvOut);
        tvDataselected =view.findViewById(R.id.tvDataSelected);
        tvRemaining = view.findViewById(R.id.tvDataRemain);
        tvTotal = view.findViewById(R.id.tvTotal);
        edtIn = view.findViewById(R.id.edtIn);
        edtOut = view.findViewById(R.id.edtOut);
        tvIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(edtIn);
            }
        });
        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(edtOut);
            }
        });
        Button btn = view.findViewById(R.id.btnShowRevenue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticsDAO statisticsDAO = new StatisticsDAO(view.getContext());
                if(!edtIn.getText().toString().matches(regexDate) || !edtOut.getText().toString().matches(regexDate)){
                    Toast.makeText(getActivity(), "Vui Lòng nhập đúng định dạng !", Toast.LENGTH_SHORT).show();
                }else{
                    int total = statisticsDAO.getDataTotal()/10;
                    int DataSelected = statisticsDAO.getDataByTimePeriod(edtIn.getText().toString(),edtOut.getText().toString())/10;
                    int remain = total - DataSelected;
                    tvTotal.setText("Tổng doanh thu: "+total+" VNĐ");
                    tvDataselected.setText("Doanh thu từ "+edtIn.getText()+" - "+edtOut.getText()+": "+DataSelected+" VNĐ");
                    tvRemaining.setText("Doanh thu những ngày còn lại: "+remain+" VNĐ");
                    ArrayList<PieEntry> entiers = new ArrayList<>();
                    entiers.add(new PieEntry((DataSelected),"Data Selected"));
                    entiers.add(new PieEntry((remain),"Remaining Data"));
                    PieDataSet pieDataSet = new PieDataSet(entiers,"Total");
                    pieDataSet.setValueTextSize(17);
                    pieDataSet.setFormSize(22);
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.animateY(1200);
                    pieChart.invalidate();
                }
            }
        });
        return view;
    }
    public void showPickerDialog(EditText edt){
        Calendar calendar = Calendar.getInstance();
        int year =        calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
                edt.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        pickerDialog.show();
    }

}
