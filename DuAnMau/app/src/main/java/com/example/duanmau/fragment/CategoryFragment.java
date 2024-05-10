package com.example.duanmau.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.Adapter.AdapterCategory_New_virsion;
import com.example.duanmau.R;
import com.example.duanmau.Sqlite.BooksDAO;
import com.example.duanmau.Sqlite.CategoryDAO;
import com.example.duanmau.model.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CategoryFragment extends Fragment {
     static ArrayList<Category> list;
     MainActivity mainActivity;
     int code;
    static AdapterCategory_New_virsion adapterCategory;
    static RecyclerView recyclerView;
    static CategoryDAO categoryDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.category_fragment,container,false);
        categoryDao= new CategoryDAO(getActivity());
        list = categoryDao.getFullData();
        adapterCategory = new AdapterCategory_New_virsion(getActivity(),list);
        recyclerView = view.findViewById(R.id.rcvCategoryFragment);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterCategory);
        return view;
    }
    }

