package com.example.duanmau.Sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.model.Books;
import com.example.duanmau.model.T10BooksBestsellers;

import java.util.ArrayList;

public class StatisticsDAO {
    Context context;
    SQLiteDatabase db;

    public StatisticsDAO(Context context) {
        DbHelper dbHelper =new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<T10BooksBestsellers> getTop10(){
        ArrayList<T10BooksBestsellers> list = new ArrayList<>();
        String sql = "Select CallCard.ID , Books.NAME, COUNT(CallCard.IDBooks) as AMOUNT FROM CallCard" +
                " INNER JOIN Books ON CallCard.IDBooks = Books.ID " +
                "GROUP BY CallCard.IDBooks ORDER BY AMOUNT DESC LIMIT 10";
        Cursor Data = db.rawQuery(sql, null);
        while (Data.moveToNext()){
            list.add(new T10BooksBestsellers(Data.getInt(0),Data.getString(1),Data.getInt(2)));
        };
        return list;
    }

    @SuppressLint("Range")

    String callcard = "Create table if not exists CallCard(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "DATEIN TEXT NOT NULL," +
            "DATEOUT TEXT NOT NULL," +
            "IDBOOKS INTEGER REFERENCES Books(ID)," +
            "IDMEMBERS INTEGER REFERENCES Members(ID)," +
            "IDLIBRARIAN TEXT REFERENCES Librarian(ID))";
    public int getDataByTimePeriod(String DateIn, String DateOut){
        int sum = 0;
        String sql = "Select SUM(PRICE) as pr  from Books Where Books.ID IN " +
                "(Select  CallCard.IDBOOKS from CallCard WHERE CallCard.DATEIN BETWEEN '"+DateIn+"' AND '"+DateOut +"')  ";
        Cursor Data = db.rawQuery(sql, null);
        while (Data.moveToNext()){
            try {
                sum = Data.getInt(0);
            }catch (Exception e){
                Log.e("Show Message","Error: " +e);
            }
        };
        return sum;

    }


    String books = "Create table if not exists Books(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL," +
            "PRICE INTEGER NOT NULL," +
            "IDCATEGORY INTEGER REFERENCES Category(ID)," +
            "IMAGE BLOB)";
    public  int getDataTotal(){
        int sum = 0;
        String sql = "Select SUM(PRICE) as pr  from Books Where Books.ID IN (Select  CallCard.IDBOOKS from CallCard)";
        Cursor getDataBooks = (Cursor) db.rawQuery(sql,null);
        while(getDataBooks.moveToNext()){
            try{
                sum = getDataBooks.getInt(0);

            }catch (Exception e){
                Log.e("Log","Error: "+ e.getMessage());
            }
        }
        return  sum;
    }
}
