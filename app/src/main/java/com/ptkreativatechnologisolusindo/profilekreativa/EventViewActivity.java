package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventViewActivity extends AppCompatActivity {

    Bitmap bitmap;
    String ID_EVENT, NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS,time2, time;
    ImageView gambar;
    TextView nama, tgl, tempat, status_aktif , kapasitas, htm;
    Button button;
    LinkDatabase linkDatabase;
    boolean status=false;
    ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
    public static VisiMisiActivity EV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        ID_EVENT = new String(); NAMA_EVENT = new String(); TGL_EVENT = new String(); TEMPAT = new String(); KAPISITAS = new String();
        HTM = new String(); FOTO_EVENT = new String();STATUS = new String(); time = new String(); time2 = new String();

        nama = (TextView) findViewById(R.id.ET_Eevent_nama);
        tgl = (TextView) findViewById(R.id.ET_Eevent_tgl);
        tempat = (TextView) findViewById(R.id.ET_Eevent_tempat);
        status_aktif = (TextView) findViewById(R.id.ET_Eevent_status);
        kapasitas = (TextView) findViewById(R.id.ET_Eevent_kapasitas);
        htm = (TextView) findViewById(R.id.ET_Eevent_htm);
        gambar = (ImageView)findViewById(R.id.IV_Eevent);
//        button = (Button) findViewById(R.id.join);

        linkDatabase = new LinkDatabase();

        getViewEvent();

//        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
//        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
//        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
//        TEMPAT = getIntent().getStringExtra("TEMPAT");
//        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
//        HTM = getIntent().getStringExtra("HTM");
//        FOTO_EVENT = getIntent().getStringExtra("FOTO_EVENT");
//        STATUS = getIntent().getStringExtra("STATUS");
//
//        nama.setText(NAMA_EVENT);
//        tgl.setText(TGL_EVENT);
//        tempat.setText(TEMPAT);
//        status_aktif.setText(STATUS);
//        kapasitas.setText(KAPISITAS);
//        htm.setText(HTM);

//        gambar.setVisibility(View.VISIBLE);
//        Glide.with(this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(gambar);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EventViewActivity.this, RegistActivity.class);
//                intent.putExtra("ID_EVENT", ID_EVENT);
//                intent.putExtra("NAMA_EVENT", NAMA_EVENT);
//                intent.putExtra("TGL_EVENT", TGL_EVENT);
//                intent.putExtra("TEMPAT",TEMPAT);
//                intent.putExtra("KAPISITAS", KAPISITAS);
//                intent.putExtra("HTM", HTM);
//                intent.putExtra("FOTO_EVENT", FOTO_EVENT);
//                intent.putExtra("STATUS", STATUS);
//                startActivity(intent);
//            }
//        });

    }

    private void getViewEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url      =   linkDatabase.linkurl()+"event.php?operasi=view_event";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    ID_EVENT = jsonObject.getString("ID_EVENT");
                    NAMA_EVENT = jsonObject.getString("NAMA_EVENT");
                    TGL_EVENT = jsonObject.getString("TGL_EVENT");
                    TEMPAT = jsonObject.getString("TEMPAT");
                    KAPISITAS = jsonObject.getString("KAPISITAS");
                    HTM = jsonObject.getString("HTM");
                    FOTO_EVENT = jsonObject.getString("FOTO_EVENT");
                    STATUS = jsonObject.getString("STATUS");

                    gambar.setVisibility(View.VISIBLE);
                    Glide.with(EventViewActivity.this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(gambar);

                    nama.setText(NAMA_EVENT);
                    tgl.setText(TGL_EVENT);
                    tempat.setText(TEMPAT);
                    status_aktif.setText(STATUS);
                    kapasitas.setText(KAPISITAS);
                    htm.setText(HTM);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);
    }

    public void onEdit(View view){
        Intent intent = new Intent(EventViewActivity.this, RegistActivity.class);
        intent.putExtra("ID_EVENT", ID_EVENT);
        intent.putExtra("NAMA_EVENT", NAMA_EVENT);
        intent.putExtra("TGL_EVENT", TGL_EVENT);
        intent.putExtra("TEMPAT",TEMPAT);
        intent.putExtra("KAPISITAS", KAPISITAS);
        intent.putExtra("HTM", HTM);
        intent.putExtra("FOTO_EVENT", FOTO_EVENT);
        intent.putExtra("STATUS", STATUS);
        startActivity(intent);
    }

//    public  void onBatal(View view){
//
//        finish();
////        finish();
////    public void onBack(View view) {finish();
//    }
}
