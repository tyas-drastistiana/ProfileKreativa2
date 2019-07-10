package com.ptkreativatechnologisolusindo.profilekreativa.Event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Event;
import com.ptkreativatechnologisolusindo.profilekreativa.DataHelper;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPesertaEventActivity extends AppCompatActivity {

    String ID_EVENT, NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, STATUS, ID_JOIN_EVENT, column1;
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
        IV_Tevent = (ImageView) findViewById(R.id.IV_Tevent);



        linkDatabase = new LinkDatabase();
        dbHelper = new DataHelper(this);

        ID_EVENT = getIntent().getStringExtra("ID_EVENT");
        NAMA_EVENT = getIntent().getStringExtra("NAMA_EVENT");
        TGL_EVENT = getIntent().getStringExtra("TGL_EVENT");
        TEMPAT = getIntent().getStringExtra("TEMPAT");
        KAPISITAS = getIntent().getStringExtra("KAPISITAS");
        HTM = getIntent().getStringExtra("HTM");
        STATUS = getIntent().getStringExtra("STATUS");


        ID_JOIN_EVENT = new String();
        column1 = new String();

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
                column1 = c.getString(1);
                String column2 = c.getString(0);
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

    public void onPilih(View view) {
//        com.solusindo.kreativa.companyprofilekreativaadmin.ImagePicker.pickImage(this, "Select Your image : ")
        com.ptkreativatechnologisolusindo.profilekreativa.ImagePicker.pickImage(this, "Select Your image : ");
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

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }

    public void onSimpan(View view) {

//        Toast.makeText(getApplicationContext(), column1.toString()+" || "+ ID_EVENT + " || ", Toast.LENGTH_LONG  ).show();


//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"join_event.php?operasi=join_event";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
                    EventActivity.ma.reload();
//                    Intent intent =- new Intent(ViewPesertaEventActivity.this, DetailPesertaActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss");
                time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                time2 = sdf2.format(new Date());

                params.put("ID_PESERTA", column1);
                params.put("ID_EVENT", ID_EVENT);
                if(bitmap!= null){
                    params.put("path", "images/"+time+"_join_event.jpg");
                    params.put("BUKTI_PEMBAYARAN", ImagetoString(bitmap));
                }
                params.put("TGL_JOIN", time2.toString());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(ViewPesertaEventActivity.this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }
        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap != null){
            IV_Tevent.setVisibility(View.VISIBLE);
            IV_Tevent.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
