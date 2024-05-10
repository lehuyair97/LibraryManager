package com.example.duanmau.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.model.Category;
import com.example.duanmau.model.Librarian;

import java.util.ArrayList;

public class LibrarianDAO {
    SQLiteDatabase db;

    public LibrarianDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Librarian Obj){
        ContentValues ct = new ContentValues();
        ct.put("ID",Obj.getID());
        ct.put("NAME",Obj.getNAME());
        ct.put("PASSWORD",Obj.getPASSWORD());
        ct.put("AVATAR",Obj.getAVATAR());
        return db.insert("Librarian",null,ct);
    }

    public int update(Librarian Obj){
        ContentValues ct = new ContentValues();
        ct.put("NAME",Obj.getNAME());
        ct.put("PASSWORD",Obj.getPASSWORD());
        ct.put("AVATAR",Obj.getAVATAR());
        return db.update("Librarian",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int delete(String id){
        return db.delete("Librarian","ID = ?",new String[]{id});
    }
    public ArrayList<Librarian> getData(){
        ArrayList<Librarian> list = new ArrayList<>();
        String sql = "Select * from Librarian";
        Cursor getDataLibrarian = (Cursor) db.rawQuery(sql,null);
        while(getDataLibrarian.moveToNext()){
            list.add(new Librarian(getDataLibrarian.getString(0),getDataLibrarian.getString(1),
                    getDataLibrarian.getString(2),getDataLibrarian.getBlob(3)));
        }
        return  list;
    }

}
