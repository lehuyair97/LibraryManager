package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.Adapter_CallCard_New_Virsion;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.CallCardDAO;
import com.example.duanmau.model.Callcard;

import java.util.ArrayList;

public class CallCardFragment extends Fragment {
    ArrayList<Callcard> list;
    CallCardDAO callCardDAO;
    Adapter_CallCard_New_Virsion adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.callcard_fragment,container,false);
        recyclerView = view.findViewById(R.id.rcvCallCard);
        adapter = new Adapter_CallCard_New_Virsion(getActivity());
        callCardDAO = new CallCardDAO(getActivity());
        list = new ArrayList<>();
        list = callCardDAO.getData();
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
