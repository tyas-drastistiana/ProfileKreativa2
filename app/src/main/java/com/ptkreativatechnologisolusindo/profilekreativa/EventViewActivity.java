package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;

public class EventViewActivity extends AppCompatActivity {

    Bitmap bitmap;
    String ID_EVENT,NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS,time2, time;
    ImageView gambar;
    TextView nama, tgl, tempat, status_aktif , kapasitas, htm;
    Button button;
    LinkDatabase linkDatabase;
    boolean status=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        nama = (TextView) findViewById(R.id.ET_Eevent_nama);
        tgl = (TextView) findViewById(R.id.ET_Eevent_tgl);
        tempat = (TextView) findViewById(R.id.ET_Eevent_tempat);
        status_aktif = (TextView) findViewById(R.id.ET_Eevent_status);
        kapasitas = (TextView) findViewById(R.id.ET_Eevent_kapasitas);
        htm = (TextView) findViewById(R.id.ET_Eevent_htm);
        gambar = (ImageView)findViewById(R.id.IV_Eevent);
        button = (Button) findViewById(R.id.join);

        linkDatabase = new LinkDatabase();


        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
        TEMPAT = getIntent().getStringExtra("TEMPAT");
        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
        HTM = getIntent().getStringExtra("HTM");
        FOTO_EVENT = getIntent().getStringExtra("FOTO_EVENT");
        STATUS = getIntent().getStringExtra("STATUS");

        nama.setText(NAMA_EVENT);
        tgl.setText(TGL_EVENT);
        tempat.setText(TEMPAT);
        status_aktif.setText(STATUS);
        kapasitas.setText(KAPISITAS);
        htm.setText(HTM);

        gambar.setVisibility(View.VISIBLE);
        Glide.with(this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(gambar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventViewActivity.this, PendaftaranEventActivity.class);
                startActivity(intent);
            }
        });

    }

    public  void onBack(View view){
        finish();
//    public void onBack(View view) {finish();
    }
}
