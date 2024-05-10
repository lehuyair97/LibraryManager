package com.example.duanmau.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.duanmau.fragment.CategoryFragment;

public class DbHelper extends SQLiteOpenHelper {
    public static String NAME_DATABASE = "PNLIB.sqlite";
    public static int VERSION = 1;
    public DbHelper(@Nullable Context context) {
        super(context, NAME_DATABASE, null, VERSION);
    }



    String category = "Create table if not exists Category(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT," +
            "IMAGE BLOB)";
    String librarian = "Create table if not exists Librarian(" +
            "ID TEXT PRIMARY KEY," +
            "NAME TEXT NOT NULL," +
            "PASSWORD TEXT NOT NULL," +
            "AVATAR BLOB)";
    String members = "Create table if not exists Members(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL," +
            "DATE TEXT NOT NULL,"+
            "IMAGE BLOB)";
    ;
    String books = "Create table if not exists Books(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL," +
            "PRICE INTEGER NOT NULL," +
            "IDCATEGORY INTEGER REFERENCES Category(ID)," +
            "IMAGE BLOB)";

    String callcard = "Create table if not exists CallCard(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "DATEIN TEXT NOT NULL," +
            "DATEOUT TEXT NOT NULL," +
            "IDBOOKS INTEGER REFERENCES Books(ID)," +
            "IDMEMBERS INTEGER REFERENCES Members(ID)," +
            "IDLIBRARIAN TEXT REFERENCES Librarian(ID))";


    String BooksData = "insert into Books Values" +
            "(null,'The Learn StartUp',145000,1,null)," +
            "(null,'Kinh Điển về Khởi Nghiệp',188000,1,null)," +
            "(null,'Thế Giới Phẳng',265000,1,null)," +
            "(null,'Ươm Mầm',109000,2,null)," +
            "(null,'Khuyến Học',65000,2,null)," +
            "(null,'Tôi Tự Học',75000,2,null)," +
            "(null,'Lược Sử Loài Người',240000,3,null)," +
            "(null,'Trăm Năm Nhìn Lại',230000,3,null)," +
            "(null,'Súng, Vi Trùng và Thép',288000,3,null)," +
            "(null,'Nhà Đầu Tư Thông Minh',160000,4,null)," +
            "(null,'Giàu Từ Chứng Khoán',120000,4,null)," +
            "(null,'Cha Giàu Cha Nghèo (Trọn bộ)',1000000,4,null)," +
            "(null,'To Kill a Mockingbird',175000,5,null)," +
            "(null,'Không Gia Đình',170000,5,null)," +
            "(null,'Nhà Giả Kim',63000,5,null)," +
            "(null,'Think And Grow Rich',100000,6,null)," +
            "(null,'Đắc Nhân Tâm',120000,6,null)," +
            "(null,'Đừng Quên Não Để Đời Bớt Bão',69000,6,null)" ;
    String MembersData = "insert into Members Values" +
            "(null,'Nguễn Khánh An','2000',null),"+
            "(null,'Hoàng Văn Bảo','2000',null)," +
            "(null,'Nguyễn Kim Liên','2002',null)," +
            "(null,'Lê Văn Sỹ','2002',null)," +
            "(null,'Đỗ Trung An','2003',null)," +
            "(null,'Lê Quốc Trung','2001',null)," +
            "(null,'Đặng Nữ Ngọc','2000',null)," +
            "(null,'Trần Trung Hiếu','2003',null)," +
            "(null,'Lê Diệp Anh','2004',null)," +
            "(null,'Đỗ Sỹ Hùng','2002',null)";


    String LibrarianData = "insert into Librarian Values" +
            "('vankhanh01','Trần Văn Khánh','123123',null)," +
            "('giabao123','Lê Gia Bảo','123123',null)," +
            "('admin','Administration','admin',null)";

    String CategoryData = "insert into Category Values"+
            "(null,'Kinh Tế', null),"+
            "(null,'Giáo dục', null),"+
            "(null,'Lịch sử', null),"+
            "(null,'Đầu tư', null),"+
            "(null,'Tiểu thuyết', null),"+
            "(null,'Self-Help', null)";
    String CallCardData= "insert into CallCard Values" +
        "(null,'2023/06/01','2023/07/01',1,1,'admin')," +
        "(null,'2023/06/01','2023/07/01',1,1,'admin')," +
        "(null,'2023/06/01','2023/07/01',1,1,'admin')," +
        "(null,'2023/06/01','2023/07/01',2,2,'admin')," +
        "(null,'2023/06/01','2023/07/01',2,2,'admin')," +
        "(null,'2023/07/01','2023/08/01',3,3,'admin')," +
        "(null,'2023/07/01','2023/08/01',4,4,'admin')," +
        "(null,'2023/08/01','2023/09/01',5,5,'admin')," +
        "(null,'2023/08/01','2023/09/01',6,6,'admin')," +
        "(null,'2023/09/01','2023/10/01',7,7,'admin')," +
        "(null,'2023/09/01','2023/10/01',8,8,'admin')," +
        "(null,'2023/09/01','2023/10/01',9,9,'admin')," +
        "(null,'2023/10/01','2023/11/01',10,9,'admin')," +
        "(null,'2023/10/01','2023/11/01',11,10,'admin')," +
        "(null,'2023/10/01','2023/11/01',12,10,'admin'),"+
            "(null,'2023/20/01','2023/11/01',13,10,'admin')";
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        //set Table Database
        sdb.execSQL(category);
        sdb.execSQL(librarian);
        sdb.execSQL(members);
        sdb.execSQL(books);
        sdb.execSQL(callcard);
        //Set Data
        sdb.execSQL(CategoryData);
        sdb.execSQL(BooksData);
        sdb.execSQL(MembersData);
        sdb.execSQL(LibrarianData);
        sdb.execSQL(CallCardData);

    }
    public Cursor getData (String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
        public void drop(String tableNames){
            SQLiteDatabase sdb = getWritableDatabase();
            String sql = "DROP TABLE IF EXISTS '"+tableNames+"'";
            sdb.execSQL(sql);
        }
    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        drop("Librarian");
        drop("Members");
        drop("Books");
        drop("Category");
        drop("CallCard");
        onCreate(sdb);
    }
}
