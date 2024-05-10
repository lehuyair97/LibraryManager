package com.example.duanmau.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.model.Books;
import com.example.duanmau.model.Category;
import com.example.duanmau.model.Librarian;

import java.util.ArrayList;

public class CategoryDAO {
    SQLiteDatabase db;

    public CategoryDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Category Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("IMAGE",Obj.getIMG());
        return db.insert("Category",null,ct);
    }

    public int update(Category Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("IMAGE",Obj.getIMG());
        return db.update("Category",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int updateFull(Category Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("IMAGE",Obj.getIMG());
        return db.update("Category",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int updateImage(int index,byte[] img){
       try{
           ContentValues ct = new ContentValues();
           ct.put("IMAGE",img);
           Log.e("tag","Update thanh cong");
           return db.update("Category",ct,"ID = ?",new String[]{String.valueOf(index)});
       }catch(Exception e) {
           Log.e("Tag", "Error: "+ e.getMessage());
       }
       return  0;
    }


    public int delete(String id){
        return db.delete("Category","ID = ?",new String[]{id});
    }




    public ArrayList<Category> getFullData(){
        ArrayList<Category> list = new ArrayList<>();
        String sql = "Select * from Category";
        Cursor getDataCategory = (Cursor) db.rawQuery(sql,null);
        while(getDataCategory.moveToNext()){
                list.add(new Category(getDataCategory.getInt(0),getDataCategory.getString(1),getDataCategory.getBlob(2)));
        }
        return  list;
    }


    public String getNameCategoryByID(int IDCategory){
        String dataOut = "";
        String sql = "Select * from Category where ID = '"+IDCategory+"'";
        Cursor getDataCategory = (Cursor) db.rawQuery(sql,null);
        while(getDataCategory.moveToNext()){
            dataOut += getDataCategory.getString(1);
        }
        return  dataOut;
    }
}
