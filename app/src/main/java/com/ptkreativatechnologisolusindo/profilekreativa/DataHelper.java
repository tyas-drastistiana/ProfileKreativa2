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
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table user (id text null, id_peserta text null, nama text null, nohp text null, pend text null, jlk text null, alm text null, email text null);";
        Log.d("user", "onCreate: "+sql);
        db.execSQL(sql);

        db.execSQL("insert into user(id, id_peserta,  nama, nohp , pend , jlk , alm , email ) values('" +
                "1" + "','" +
                "" + "','" +
                ""+ "','" +
                ""+ "','" +
                ""+ "','" +
                ""+ "','" +
                ""+ "','" +
                ""+"')");
//        sql = "INSERT INTO user (id, nama, nohp , pend , jlk , alm , email ) " +
//                "VALUES ('1', 'tyas','097522345678','smk','p','gresik','tyas@gmail.com');";
//        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}
