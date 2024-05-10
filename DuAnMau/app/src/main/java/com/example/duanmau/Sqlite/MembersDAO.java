package com.example.duanmau.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.model.Books;
import com.example.duanmau.model.Members;

import java.util.ArrayList;

public class MembersDAO {
    SQLiteDatabase db;

    public MembersDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Members Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("DATE",Obj.getDATE());
        ct.put("IMAGE",Obj.getIMAGE());
        return db.insert("Members",null,ct);
    }

    public int update(Members Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("DATE",Obj.getDATE());
        ct.put("IMAGE",Obj.getIMAGE());
        return db.update("Members",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int updateImage(int index,byte[] img){
        try{
            ContentValues ct = new ContentValues();
            ct.put("IMAGE",img);
            Log.e("tag","Update thanh cong");
            return db.update("Members",ct,"ID = ?",new String[]{String.valueOf(index)});
        }catch(Exception e) {
            Log.e("Tag", "Error: "+ e.getMessage());
        }
        return  0;
    }

    public int delete(String id){
        return db.delete("Members","ID = ?",new String[]{id});

    }
    public ArrayList<Members> getData(){
        ArrayList<Members> list = new ArrayList<>();
        String sql = "Select * from Members";
        Cursor getDataMembers = (Cursor) db.rawQuery(sql,null);
        while(getDataMembers.moveToNext()){
            list.add(new Members(getDataMembers.getInt(0),getDataMembers.getString(1),
                    getDataMembers.getString(2),getDataMembers.getBlob(3)));
        }
        return  list;
    }
}
