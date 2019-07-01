package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Event;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;

import java.util.List;

public class ViewPesertaEventActivity extends AppCompatActivity {


    String ID_EVENT, NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS,
            NAMA_PESERTA, NO_HP, FOTO_BUKTI_PEMBAYARAN, PEND, JENIS_KELAMIN, ALAMAT_PESERTA, EMAIL_PESERTA;
    TextView event_nama, event_tglpel, event_tempat, event_htm, ET_Eevent_nama, ET_Eevent_tgl, ET_Eevent_tempat, ET_Eevent_htm;
    TextView nama, alm, telp, jlk, pend, email;
    //    RadioButton lk, pr;
//    RadioGroup rg_kel;
    ImageView IV_Tevent;
    Spinner SP_event_pend, SP_event_kel;
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
//    private List<Peserta> lstData;
    private List<Event> lstDataevent;
    ProgressDialog progressDialog;
    String time2, time;
    boolean status=false;
    public static ViewPesertaEventActivity ma;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_peserta_event);

        event_nama = (TextView) findViewById(R.id.ET_Eevent_nama);
        event_tglpel = (TextView) findViewById(R.id.ET_Eevent_tgl);
        event_tempat = (TextView) findViewById(R.id.ET_Eevent_tempat);
        event_htm = (TextView) findViewById(R.id.ET_Eevent_htm);

        nama = (TextView) findViewById(R.id.ET_event_nama_peserta);
        alm = (TextView) findViewById(R.id.ET_event_alamat);
        telp = (TextView) findViewById(R.id.ET_event_hp);
        jlk = (TextView) findViewById(R.id.jlk);
        pend = (TextView) findViewById(R.id.pend);
        email = (TextView) findViewById(R.id.ET_event_email);

        linkDatabase = new LinkDatabase();
        dbHelper = new DataHelper(this);

        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
        TEMPAT = getIntent().getStringExtra("TEMPAT");
        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
        HTM = getIntent().getStringExtra("HTM");
        STATUS = getIntent().getStringExtra("STATUS");


        event_nama.setText(NAMA_EVENT);
        event_tglpel.setText(TGL_EVENT);
        event_tempat.setText(TEMPAT);
        event_htm.setText(HTM);
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

    public  void onBack(View view){
        finish();
//    public void onBack(View view) {finish();
    }

    public void reload(){
//        lstData.clear();
//        myAdapter.notifyDataSetChanged();
//
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        initDataset();
    }
}
