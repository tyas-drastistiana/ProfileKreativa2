package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.adapter.AdapterFasilitas;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Fasilitas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FasilitasActivity extends AppCompatActivity {
    private RecyclerView rv_fasilitas;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Fasilitas> lstData;
    private AdapterFasilitas myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;
    public static FasilitasActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);

        rv_fasilitas = findViewById(R.id.RV_fasilitas);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final Context context = this;

        initDataset();
    }

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"fasilitas.php?operasi=view_fasilitas";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Fasilitas model = new Fasilitas();
                        model.setID_FASILITAS(jsonObject.getString("ID_FASILITAS"));
                        model.setDESK_FASILITAS(jsonObject.getString("DESK_FASILITAS"));
                        model.setFOTO_FASILITAS(jsonObject.getString("FOTO_FASILITAS"));
                        model.setTANGGAL_FASILITAS(jsonObject.getString("TANGGAL_FASILITAS"));

                        lstData.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setRvadapter(lstData);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString()); progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(FasilitasActivity.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Fasilitas> lst) {
        myAdapter       =   new AdapterFasilitas(FasilitasActivity.this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
        rv_fasilitas.setLayoutManager(layoutManager);
        rv_fasilitas.setAdapter(myAdapter);
    }

    public void onBack(View view) {finish();
    }

}
