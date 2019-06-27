package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_peserta_event);

        event_nama = (TextView) findViewById(R.id.ET_Eevent_nama);
        event_tglpel = (TextView) findViewById(R.id.ET_Eevent_tgl);
        event_tempat = (TextView) findViewById(R.id.ET_Eevent_tempat);
        event_htm = (TextView) findViewById(R.id.ET_Eevent_htm);

        linkDatabase = new LinkDatabase();

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
