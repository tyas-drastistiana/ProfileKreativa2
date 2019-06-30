package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    TextView nama, alm, telp, jlk, pend, email;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest ArrayRequest;
    String str_nama, str_alamat, str_tlp, str_email,str_jlk,
            str_pend, id;
    ProgressDialog progressDialog;
    DataHelper dbHelper;

    public static ProfileActivity PA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nama = (TextView) findViewById(R.id.ET_event_nama_peserta);
        alm = (TextView) findViewById(R.id.ET_event_alamat);
        telp = (TextView) findViewById(R.id.ET_event_hp);
        jlk = (TextView) findViewById(R.id.TV_event_kel);
        pend = (TextView) findViewById(R.id.TV_event_pend);
        email = (TextView) findViewById(R.id.ET_event_email);

        dbHelper = new DataHelper(this);
        linkDatabase = new LinkDatabase();
//        str_nama = new String();
//        str_alamat = new String();
//        str_tlp = new String();
//        str_email = new String();
//        str_jlk = new String();
//        id = new String();
//        str_pend = new String();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        PA = this;
        selectt();

    }

    void selectt(){
        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        Cursor c = db2.rawQuery("SELECT * FROM user ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);
                String column4 = c.getString(3);
                String column5 = c.getString(4);
                String column6 = c.getString(5);
                String column7 = c.getString(6);

                nama.setText(column2);
                alm.setText(column6);
                telp.setText(column3);
                jlk.setText(column5);
                pend.setText(column4);
                email.setText(column7);

                // Do something Here with values
//                Toast.makeText(getApplicationContext(), column1+" " + column2+ "   "+column3+ "   "+column4+ "   "+column5+ "   "+column6+ "   "+column7, Toast.LENGTH_LONG).show();
            } while(c.moveToNext());
        }
        c.close();
        db2.close();
    }
}
