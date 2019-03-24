package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
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
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Profil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompanyProfileActivity extends AppCompatActivity {

    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Profil> lstData;
    public static CompanyProfileActivity PA;

    TextView nama_perusahaan, desk;
    ImageView logo;
    String url_img;
    
    String str_nama_perusahaan, str_desk, id;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        linkDatabase = new LinkDatabase();
        lstData = new ArrayList<>();
        str_nama_perusahaan = new String();
        str_desk = new String();
        id = new String();

        url_img = new String();
        logo = (ImageView) findViewById(R.id.ivPreviewimg);
        nama_perusahaan = (TextView)findViewById(R.id.nama_Perusahaan);
        desk = (TextView)findViewById(R.id.deskripsi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        PA = this;

        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    nama_perusahaan.setText(str_nama_perusahaan);
                    desk.setText(str_desk);
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

        geturlimg();


    }

    private void geturlimg() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_logo";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    url_img = linkDatabase.linkurl()+jsonObject.getString("URL_LOGO");
//                    Toast.makeText(getBaseContext(), url_img.toString(), Toast.LENGTH_LONG).show();
                    Picasso.with(getBaseContext()).load(url_img).placeholder(R.drawable.thumbnail).into(logo);
//                    Picasso.get().load(url_img).placeholder(R.drawable.thumbnail).into(logo);
//                    progressDialog.dismiss();
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

    public void refresh(){
        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    nama_perusahaan.setText(str_nama_perusahaan);
                    desk.setText(str_desk);
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

    public void onBack (View view){
        finish();
    }
}
