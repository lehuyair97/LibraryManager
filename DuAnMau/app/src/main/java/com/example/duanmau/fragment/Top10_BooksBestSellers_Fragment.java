package com.example.duanmau.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.duanmau.Adapter.AdapterTop10;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.StatisticsDAO;
import com.example.duanmau.model.T10BooksBestsellers;

import java.util.ArrayList;

public class Top10_BooksBestSellers_Fragment extends ListFragment {
    ArrayList<T10BooksBestsellers> list;
    StatisticsDAO statisticsDAO;
    AdapterTop10 adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        statisticsDAO = new StatisticsDAO(getContext());
        list = statisticsDAO.getTop10();
        Log.e("tag", "Size" + list.size() );
        ArrayList<T10BooksBestsellers> list1 = new ArrayList<>();
        adapter = new AdapterTop10(getContext(),R.layout.top10_item_layout,list);
        setListAdapter(adapter);
        return  inflater.inflate(R.layout.top10_fragment,container,false);
    }
}
