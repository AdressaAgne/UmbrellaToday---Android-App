package com.example.orangee.umbrellatoday.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by orangee on 17/09/16.
 */
public class databaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "weatherLocation.db";
    public static final String tableName = "locations";

    public static final String tableColValue = "value";
    public static final String tableColType = "type";


    public databaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (" + tableColValue + " VARCHAR(255), " + tableColType + " VARCHAR(255) PRIMARY KEY UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public boolean insertData(String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);
        cv.put(tableColType, type);

        long result = db.insert(tableName, null, cv);

        Log.d("GPS", "Running insert: " + type + " = " + value + ", result = " + result);

        return result > -1;
    }

    public boolean updateData(String type, String value){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);

        int result = db.update(tableName, cv, tableColType + " = ?", new String[] {String.valueOf(type) });

        Log.d("GPS", "Running update: " + type + " = " + value + ", result = " + result);

        return result > -1;

    }

    public String fetchType(String type){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE type = ?", new String[] {String.valueOf(type) });


        if(c.moveToFirst()){
            String val = c.getString(c.getColumnIndex(tableColValue));
            Log.d("GPS", "Fetching " + type + " = " + val);
            c.close();
            return val;
        } else {
            Log.d("GPS", "Fetching " + type + ", no rows found");
            c.close();
            return null;
        }

    }

    public void updateOrInsert(String type, String value){
       if(!insertData(type, value)) {
           updateData(type, value);
       }
    }
}
