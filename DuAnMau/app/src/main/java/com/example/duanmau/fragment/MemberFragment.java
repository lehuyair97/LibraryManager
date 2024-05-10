package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.Adapter_Member_New_Virsion;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.MembersDAO;
import com.example.duanmau.model.Members;

import java.util.ArrayList;

public class MemberFragment extends Fragment {
    ArrayList<Members> list;
    Adapter_Member_New_Virsion adapterMember;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_fragment,container,false);
        MembersDAO membersDAO = new MembersDAO(getActivity());
        list=new ArrayList<>();
        list = membersDAO.getData();
        adapterMember = new Adapter_Member_New_Virsion(getContext());
        adapterMember.setData(list);
        RecyclerView recyclerView = view.findViewById(R.id.rcvMembers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterMember);
        return view;
    }

}
