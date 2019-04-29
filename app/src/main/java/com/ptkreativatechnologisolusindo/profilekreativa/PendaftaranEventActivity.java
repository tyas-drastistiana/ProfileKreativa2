package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendaftaranEventActivity extends AppCompatActivity {

    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Peserta> lstData;
    String ID_PESERTA, NAMA_PESERTA, NO_HP, FOTO_BUKTI_PEMBAYARAN, PEND, JENIS_KELAMIN, ALAMAT_PESERTA, EMAIL_PESERTA;
    TextInputEditText nama_peserta, alamat, hp, email;
    TextView ET_Eevent_nama, ET_Eevent_tgl, ET_Eevent_tempat, ET_Eevent_htm;
//    RadioButton lk, pr;
//    RadioGroup rg_kel;
    ImageView IV_Tevent;
    Spinner SP_event_pend, SP_event_kel;
    private final int IMG_REQUEST =1; public Bitmap bitmap;
    ProgressDialog progressDialog;
    String time2, time;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran_event);

        nama_peserta = findViewById(R.id.ET_event_nama_peserta);
        alamat = findViewById(R.id.ET_event_alamat);
        hp = findViewById(R.id.ET_event_hp);
        email = findViewById(R.id.ET_event_email);
        ET_Eevent_nama = findViewById(R.id.ET_Eevent_nama);
        ET_Eevent_tgl = findViewById(R.id.ET_Eevent_tgl);
        ET_Eevent_tempat = findViewById(R.id.ET_Eevent_tempat);
        ET_Eevent_htm = findViewById(R.id.ET_Eevent_htm);
//        lk = findViewById(R.id.lk);
//        pr = findViewById(R.id.pr);
//        rg_kel = findViewById(R.id.rg_kel);
        IV_Tevent = findViewById(R.id.IV_Tevent);
        SP_event_pend = findViewById(R.id.SP_event_pend);
        SP_event_kel = findViewById(R.id.SP_event_kel);
        time = new String();time2 = new String();

        linkDatabase = new LinkDatabase();
        lstData = new ArrayList<>();
        NAMA_PESERTA = new String();
        NO_HP = new String();
        FOTO_BUKTI_PEMBAYARAN = new String();
        PEND = new String();
        JENIS_KELAMIN = new String();
        ALAMAT_PESERTA = new String();
        EMAIL_PESERTA = new String();
        ID_PESERTA = new String();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);


    }
    public  void onBack(View view){
        finish();
//    public void onBack(View view) {finish();
    }
    public void onPilih(View view){
        com.ptkreativatechnologisolusindo.profilekreativa.ImagePicker.pickImage(this, "Select Your image : ");
    }

    public void onSave(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"peserta_event.php?operasi=insert_peserta";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
                    ViewPesertaEventActivity.ma.reload();
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

                params.put("NAMA_PESERTA", nama_peserta.getText().toString());
                params.put("NO_HP", hp.getText().toString());
                params.put("JENIS_KELAMIN", SP_event_kel.getSelectedItem().toString());
                params.put("ALAMAT_PESERTA", alamat.getText().toString());
                params.put("path", "images/"+time+"_peserta_event.jpg");
                params.put("EMAIL_PESERTA", email.getText().toString());
                params.put("PENDIDIKAN_TERAKHIR", SP_event_pend.getSelectedItem().toString());
                params.put("FOTO_BUKTI_PEMBAYARAN", IV_Tevent(bitmap));

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(PendaftaranEventActivity.this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        bitmap = com.mvc.imagepicker.ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap != null){
            IV_Tevent.setVisibility(View.VISIBLE);
            IV_Tevent.setImageBitmap(bitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private String IV_Tevent(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }

    public void processfinish(String output) {
        if(output.equals("Update Data Berhasil")){
            Toast.makeText(getApplicationContext(), "Data Berhasil di Update", Toast.LENGTH_LONG).show();
            ViewPesertaEventActivity profilActivity = new ViewPesertaEventActivity();
            profilActivity.ma.reload();
            finish();
        } else {
            Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }
}
