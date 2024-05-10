package com.example.duanmau.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.model.Books;
import com.example.duanmau.model.Category;

import java.util.ArrayList;

public class BooksDAO {
    SQLiteDatabase db;

    public BooksDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Books Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("PRICE",Obj.getPRICE());
        ct.put("IDCATEGORY",Obj.getIDCATEGORY());
        ct.put("IMAGE",Obj.getIMAGE());
        return db.insert("Books",null,ct);
    }

    public int update(Books Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("PRICE",Obj.getPRICE());
        ct.put("IDCATEGORY",Obj.getIDCATEGORY());
        ct.put("IMAGE",Obj.getIMAGE());
        return db.update("Books",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int updateImage(int index,byte[] img){
        try{
            ContentValues ct = new ContentValues();
            ct.put("IMAGE", img);
            Log.e("tag","Update thanh cong");
            return db.update("Books",ct,"ID = ?",new String[]{String.valueOf(index)});
        }catch (Exception e){
            Log.e("log", "Error:  " +e.getMessage());
        }
            return  0;
    }

    public int delete(String id){
        return db.delete("Books","ID = ?",new String[]{id});

    }
    public void deleteBooksByIDCategory(int IDCategory) {
        String sql1 = "DELETE FROM Books WHERE IDCategory = ?";
        db.execSQL(sql1, new String[]{Integer.toString(IDCategory)});
    }
    public ArrayList<Books> getData(){
        ArrayList<Books> list = new ArrayList<>();
        String sql = "Select * from Books";
        Cursor getDataBooks = (Cursor) db.rawQuery(sql,null);
        while(getDataBooks.moveToNext()){

                try{
                    list.add(new Books(getDataBooks.getInt(0), getDataBooks.getString(1),
                            getDataBooks.getInt(2), getDataBooks.getInt(3), getDataBooks.getBlob(4)));
        }catch (Exception e){
                    Log.e("Log","Error: "+ e.getMessage());
                }
        }
        return  list;
    }
    public String getNameBookByID(int IDBook){
        String dataOut = "";
        String sql = "Select * from Category where ID = '"+IDBook+"'";
        Cursor getDatabook = (Cursor) db.rawQuery(sql,null);
        while(getDatabook.moveToNext()){
            dataOut = getDatabook.getString(1);
        }
        return  dataOut;
    }

}
