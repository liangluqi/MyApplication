package com.example.yuekaoliaxi01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hp on 2017/8/25.
 */

public class Sql extends SQLiteOpenHelper{
    public Sql(Context context) {
        super(context,"SuJu", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table jsk(id integer primary key autoincrement ,zhi text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
