package com.example.duanmau.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.model.Callcard;
import com.example.duanmau.model.Librarian;

import java.util.ArrayList;

public class CallCardDAO {
    SQLiteDatabase db;

    public CallCardDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Callcard Obj){
        ContentValues ct = new ContentValues();
        ct.put("IDBOOKS",Obj.getIDBOOKS());
        ct.put("IDLIBRARIAN",Obj.getIDLIBRARIAN());
        ct.put("IDMEMBERS",Obj.getIDMEMBERS());
        ct.put("DATEIN",Obj.getDATEIN());
        ct.put("DATEOUT",Obj.getDATEOUT());
        return db.insert("CallCard",null,ct);
    }

    public int update(Callcard Obj){
        ContentValues ct = new ContentValues();
        ct.put("IDBOOKS",Obj.getIDBOOKS());
        ct.put("IDLIBRARIAN",Obj.getIDLIBRARIAN());
        ct.put("IDMEMBERS",Obj.getIDMEMBERS());
        ct.put("DATEIN",Obj.getDATEIN());
        ct.put("DATEOUT",Obj.getDATEOUT());

        return db.update("CallCard",ct,"ID = ?",new String[]{String.valueOf(Obj.getID())});
    }

    public int delete(String id){
        return db.delete("CallCard","ID = ?",new String[]{id});
    }
    public ArrayList<Callcard> getData(){
        ArrayList<Callcard> list = new ArrayList<>();
        String sql = "Select * from Callcard";
        Cursor getDataCallcard = (Cursor) db.rawQuery(sql,null);
        while(getDataCallcard.moveToNext()){
            list.add(new Callcard(getDataCallcard.getInt(0),getDataCallcard.getString(1),
                    getDataCallcard.getString(2),getDataCallcard.getInt(3),
                    getDataCallcard.getInt(4),getDataCallcard.getString(5)));
        };
        return  list;
    }

    public void deleteCallCardByIDCategory(int IDCategory){
        String sql1 = "Delete From CallCard Where CallCard.ID IN  (Select Books.ID from Books INNER JOIN CATEGORY " +
                "on Books.IDCATEGORY = CATEGORY.ID Where CATEGORY.ID = ?)";
        db.execSQL(sql1, new Object[]{IDCategory});
    }
    public int deleteByBookID(String id){
        return db.delete("CallCard","IDBOOKS = ?",new String[]{id});
    }
    public int deleteByMemberID(String id){
        return db.delete("CallCard","IDMEMBERS = ?",new String[]{id});
    }
}
