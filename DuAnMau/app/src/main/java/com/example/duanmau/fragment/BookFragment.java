package com.example.duanmau.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.Adapter_Books_New_Virsion;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.model.Books;
import com.example.duanmau.model.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class BookFragment extends Fragment {
    ArrayList<Books> list;
    Adapter_Books_New_Virsion adapterBooksNewVirsion;
    RecyclerView recyclerView;
    BooksDAO booksDAO;
    public static int code ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.book_fragment_new_virsion,container,false);
        booksDAO= new BooksDAO(getActivity());
        list = new ArrayList<>();
        list = booksDAO.getData();
        recyclerView = view.findViewById(R.id.RcvBookFragment);
        adapterBooksNewVirsion = new Adapter_Books_New_Virsion(getActivity());
        adapterBooksNewVirsion.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterBooksNewVirsion);
        return view;
    }




}
