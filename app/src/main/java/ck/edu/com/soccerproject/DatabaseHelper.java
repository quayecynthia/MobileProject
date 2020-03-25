package ck.edu.com.soccerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.CyclicBarrier;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Matches.db";
    public static final String TABLE_NAME = "matches_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "OPPENENT";
    public static final String COL_3 = "SCORE";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "LOCATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, OPPENENT TEXT, SCORE TEXT, DATE TEXT, LOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String opponent, String date, String score, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, opponent);
        contentValues.put(COL_3, score);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, location);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else if(opponent == "" || date == "" || score == "" || location == ""){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+ TABLE_NAME, null);

        return result;
    }

}
