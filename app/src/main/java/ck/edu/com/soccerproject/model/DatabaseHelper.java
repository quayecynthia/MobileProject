package ck.edu.com.soccerproject.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Matches.db";
    public static final String TABLE_NAME = "matches_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_TEAM";
    public static final String COL_3 = "SECOND_TEAM";
    public static final String COL_4 = "SCORE";
    public static final String COL_5 = "DATE";
    public static final String COL_6 = "LOCATION";
    public static final String COL_7 = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_TEAM TEXT NOT NULL, SECOND_TEAM TEXT NOT NULL, SCORE TEXT NOT NULL, DATE TEXT NOT NULL, LOCATION TEXT NOT NULL, IMAGE BLOB NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String first_team, String second_team, String score, String date, String location, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, first_team);
        contentValues.put(COL_3, second_team);
        contentValues.put(COL_4, score);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, location);
        contentValues.put(COL_7, image);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else if(first_team == "" || date == "" || score == "" || location == ""){
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

    public void exportData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+ TABLE_NAME, null);

        if (result.getCount()>5){
            //new ExternalDatabase().execute(result);
        }
    }

}
