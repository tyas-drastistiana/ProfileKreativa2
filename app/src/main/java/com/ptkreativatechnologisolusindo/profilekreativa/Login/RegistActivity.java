package com.ptkreativatechnologisolusindo.profilekreativa.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {

    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Peserta> lstData;
    String NAMA_PESERTA, NO_HP, PENDIDIKAN_TERAKHIR, JENIS_KELAMIN, ALAMAT_PESERTA, EMAIL_PESERTA, PASSWORD;
    TextInputEditText nama_peserta, alamat, hp, email, pwd;
    Spinner SP_event_pend, SP_event_kel;
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    ImageView iv_foto;
    ProgressDialog progressDialog;
    String time2, time;
    boolean status=false;

    private Button bt_simpan;

    public static RegistActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Form Registrasi");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        iv_foto = findViewById(R.id.aa_thumbnail);
        nama_peserta = findViewById(R.id.ET_event_nama_peserta);
        alamat = findViewById(R.id.ET_event_alamat);
        hp = findViewById(R.id.ET_event_hp);
        email = findViewById(R.id.ET_event_email);
        pwd = findViewById(R.id.ET_event_pwd);
        SP_event_pend = findViewById(R.id.SP_event_pend);
        SP_event_kel = findViewById(R.id.SP_event_kel);
        bt_simpan = findViewById(R.id.bt_simpan);
        time = new String();time2 = new String();

        linkDatabase = new LinkDatabase();
        lstData = new ArrayList<>();

        NAMA_PESERTA = new String();
        NO_HP = new String();
        PENDIDIKAN_TERAKHIR = new String();
        JENIS_KELAMIN = new String();
        ALAMAT_PESERTA = new String();
        EMAIL_PESERTA = new String();
        PASSWORD = new String();

//        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
//        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = true;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.setTitle("Form Registrasi");
//                    isShow = true;
//                } else if(isShow) {
//                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
//                    isShow = false;
//                }
//            }
//        });

    }

    public void onSave(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String upload_url = linkDatabase.linkurl()+"register_peserta.php?operasi=insert_peserta";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                if(response.toLowerCase().toString().equals("upload data berhasil")){
                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                    startActivity(intent);
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
                params.put("EMAIL_PESERTA", email.getText().toString());
                params.put("PASSWORD", pwd.getText().toString());
                params.put("PENDIDIKAN_TERAKHIR", SP_event_pend.getSelectedItem().toString());

                return params;
            }
        };
        requestQueue    =   Volley.newRequestQueue(RegistActivity.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
