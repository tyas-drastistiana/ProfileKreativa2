package com.ptkreativatechnologisolusindo.profilekreativa.Event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.FullScreenActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailPesertaActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonArrayRequest ArrayRequest;
    ProgressDialog progressDialog;
    LinkDatabase linkDatabase;
    String ID_PESERTA, ID_EVENT, str_nama, str_alamat, str_notelp, str_jenisK, str_pendidikan, str_status, str_id_join_event,
            str_bukti_pembayaran, str_tgl;
    TextView tv_nama, tv_alamat, tv_no_telp, tv_jenisK, tv_pendidikanT, sp_status;
    ImageView IV_bukti;


    public static DetailPesertaActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_peserta);

        linkDatabase = new LinkDatabase();
        ID_PESERTA = new String(); ID_EVENT = new String(); str_nama = new String(); str_alamat = new String();str_notelp = new String();
        str_jenisK = new String(); str_pendidikan = new String(); str_status = new String(); str_id_join_event = new String();
        str_bukti_pembayaran = new String(); str_tgl = new String();
        linkDatabase = new LinkDatabase();

        ID_PESERTA = getIntent().getStringExtra("ID_PESERTA");
        ID_EVENT = getIntent().getStringExtra("ID_EVENT");

        tv_nama = findViewById(R.id.TV_dp_namap);
        tv_alamat = findViewById(R.id.TV_dp_alamat);
        tv_no_telp = findViewById(R.id.TV_dp_nohp);
        tv_jenisK = findViewById(R.id.TV_dp_jenisKelamin);
        tv_pendidikanT = findViewById(R.id.TV_dp_pendidikanTerakhir);
        sp_status = findViewById(R.id.SP_dp_status);
        IV_bukti = findViewById(R.id.IV_dp_BuktiP);

        initDataset();
    }

    private void initDataset() {
        progressDialog = new ProgressDialog(DetailPesertaActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String URL      =   linkDatabase.linkurl()+"peserta_event.php?operasi=cek_peserta&id_peserta="
                +String.valueOf(ID_PESERTA)+"&id_event="+String.valueOf(ID_EVENT);
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

//                for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(0);
//                    Join_event model = new Join_event();
//                        model.setID_JOIN_EVENT(jsonObject.getString("ID_JOIN_EVENT"));
//                        model.setNAMA_PESERTA(jsonObject.getString("NAMA_PESERTA"));
//                        model.setNO_HP(jsonObject.getString("NO_HP"));
//                        model.setPENDIDIKAN_TERAKHIR(jsonObject.getString("PENDIDIKAN_TERAKHIR"));
//                        model.setJENIS_KELAMIN(jsonObject.getString("JENIS_KELAMIN"));
//                        model.setALAMAT_PESERTA(jsonObject.getString("ALAMAT_PESERTA"));
//                        model.setBUKTI_PEMBAYARAN(jsonObject.getString("BUKTI_PEMBAYARAN"));
//                        model.setSTATUS_JOIN(jsonObject.getString("STATUS_JOIN"));
//                        model.setTGL_JOIN(jsonObject.getString("TGL_JOIN"));
                    str_id_join_event = jsonObject.getString("ID_JOIN_EVENT");
                    str_nama = jsonObject.getString("NAMA_PESERTA");
                    str_notelp = jsonObject.getString("NO_HP");
                    str_pendidikan = jsonObject.getString("PENDIDIKAN_TERAKHIR");
                    str_jenisK = jsonObject.getString("JENIS_KELAMIN");
                    str_alamat = jsonObject.getString("ALAMAT_PESERTA");
                    str_bukti_pembayaran = jsonObject.getString("BUKTI_PEMBAYARAN");
                    str_status = jsonObject.getString("STATUS_JOIN");
                    str_tgl = jsonObject.getString("TGL_JOIN");

                    tv_nama.setText(str_nama);
                    tv_alamat.setText(str_alamat);
                    tv_no_telp.setText(str_notelp);
                    tv_jenisK.setText(str_jenisK);
                    tv_pendidikanT.setText(str_pendidikan);
                    sp_status.setText(str_status);
//                        Toast.makeText(getApplicationContext(), str_id_join_event.toString(), Toast.LENGTH_LONG).show();
                    if(str_bukti_pembayaran != null) {
                        Glide.with(DetailPesertaActivity.this).load(linkDatabase.linkurl() + str_bukti_pembayaran).
                                placeholder(R.drawable.thumbnail).into(IV_bukti);
                    }

//                    if (str_status != null) {
//                        int posisi = adapter.getPosition(str_status);
//                        sp_status.setSelection(posisi);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(DetailPesertaActivity.this);
        requestQueue.add(ArrayRequest);
    }

    public void onBack(View view) {
        finish();
    }

    public void onBuktip(View view) {
        if(str_bukti_pembayaran != null){
            Intent intent = new Intent(getBaseContext(), FullScreenActivity.class);
            intent.putExtra("link", str_bukti_pembayaran);
            startActivity(intent);
        }
    }

}

