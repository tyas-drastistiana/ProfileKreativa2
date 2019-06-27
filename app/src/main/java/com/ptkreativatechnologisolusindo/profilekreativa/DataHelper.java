package com.ptkreativatechnologisolusindo.profilekreativa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table user(id integer primary key, nama text null, nohp interger null, pend text null, jlk text null, alm text null, email text null);";
        Log.d("user", "onCreate: "+sql);
        db.execSQL(sql);
//        sql = "INSERT INTO user (id, nama, nohp , pend , jlk , alm , email ) " +
//                "VALUES ('1', 'tyas','097522345678','smk','p','gresik','tyas@gmail.com');";
//        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
