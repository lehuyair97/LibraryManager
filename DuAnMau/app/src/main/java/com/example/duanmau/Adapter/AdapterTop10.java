package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duanmau.Activity.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.model.T10BooksBestsellers;

import java.util.ArrayList;

public class AdapterTop10 extends BaseAdapter {
    public Context context;
    public int Layout;
    public ArrayList<T10BooksBestsellers> list;

    public AdapterTop10(Context context, int layout, ArrayList<T10BooksBestsellers> list) {
        this.context = context;
        this.Layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView name, amount;
        ImageView imgOrdinal;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if(view == null){
        viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(Layout,null);
        viewHolder.name = view.findViewById(R.id.tvNameBookTop);
        viewHolder.amount = view.findViewById(R.id.tvAmount);
        viewHolder.imgOrdinal = view.findViewById(R.id.imgOrdinalNumber);
        view.setTag(viewHolder);
    }else{
        viewHolder = (ViewHolder)view.getTag();
    }
    T10BooksBestsellers t10BooksBestsellers;
    t10BooksBestsellers = list.get(i);
    ArrayList<Integer> listPicture = getDataPicture();
    viewHolder.name.setText(list.get(i).getNAME());
    viewHolder.amount.setText(String.valueOf(list.get(i).getAMOUNT()));
    viewHolder.imgOrdinal.setImageResource(listPicture.get(i));
        return view;
    }
    public ArrayList<Integer>  getDataPicture(){
        ArrayList<Integer> listPicture;
        listPicture = new ArrayList<>();
        listPicture.add(new Integer(R.drawable.first));
        listPicture.add(new Integer(R.drawable.second));
        listPicture.add(new Integer(R.drawable.third));
        listPicture.add(new Integer(R.drawable.ordinal_number4));
        listPicture.add(new Integer(R.drawable.ordinal_number5));
        listPicture.add(new Integer(R.drawable.ordinal_number6));
        listPicture.add(new Integer(R.drawable.ordinal_number7));
        listPicture.add(new Integer(R.drawable.ordinal_number8));
        listPicture.add(new Integer(R.drawable.ordinal_number9));
        listPicture.add(new Integer(R.drawable.ordinal_number10));
        return  listPicture;
    }
}
