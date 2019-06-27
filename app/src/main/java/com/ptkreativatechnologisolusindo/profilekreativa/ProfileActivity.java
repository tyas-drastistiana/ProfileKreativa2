package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    TextView nama, alm, telp, jlk, pend, email;
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    String str_nama, str_alamat, str_tlp, str_email,str_jlk,
            str_pend, id;
    ProgressDialog progressDialog;

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

        linkDatabase = new LinkDatabase();
        str_nama = new String();
        str_alamat = new String();
        str_tlp = new String();
        str_email = new String();
        str_jlk = new String();
        id = new String();
        str_pend = new String();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        PA = this;

        String url      =   linkDatabase.linkurl()+"peserta_event.php?operasi=view_peserta";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    str_nama = jsonObject.getString("NAMA_PESERTA");
                    str_alamat = jsonObject.getString("ALAMAT_PESERTA");
                    str_email = jsonObject.getString("EMAIL");
                    str_tlp = jsonObject.getString("NO_HP");
                    str_jlk = jsonObject.getString("JENIS_KELAMIN");
                    str_email = jsonObject.getString("EMAIL_PESERTA");
                    str_pend =jsonObject.getString("PEND");


                    nama.setText(str_nama);
                    alm.setText(str_alamat);
                    telp.setText(str_tlp);
                    email.setText(str_email);
                    jlk.setText(str_jlk);
                    pend.setText(str_pend);
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
}
