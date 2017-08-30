package com.example.yuekaoliaxi01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hp on 2017/8/25.
 */

public class SqlUtils {

    private  SQLiteDatabase db;
    public  SqlUtils (Context context){
        Sql sql = new Sql(context);
         db = sql.getWritableDatabase();
    }
    public  boolean add(String zhi){
        ContentValues values = new ContentValues();
        values.put("zhi",zhi);
        long insert = db.insert("jsk", null, values);
        if (insert!=-1){
            return true;
        }else {
            return  false;
        }

    }

    public  String  cz(){

        Cursor jsk = db.query(false, "jsk", null, null, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        if (jsk.moveToNext()){
            String zhi = jsk.getString(jsk.getColumnIndex("zhi"));
            buffer.append(zhi);
            return buffer.toString();
        }

        return  "";
    }

}
