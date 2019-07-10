package com.ptkreativatechnologisolusindo.profilekreativa.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Berita;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.Adapter.AdapterBerita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BeritaFragment extends Fragment {

    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private static final Object TAG = "RecyclerViewFragment";
    private RecyclerView rv_berita;
    private List<Berita> lstData;
    private AdapterBerita myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;
    Context mcontext;


    public static BeritaFragment ma;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

//
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

//        final Context context = this;

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_berita, container, false);

        rv_berita = (RecyclerView) v.findViewById(R.id.recyclerview_berita);

        return v;
    }

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"berita.php?operasi=view_berita";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Berita model = new Berita();
                        model.setID_BERITA(jsonObject.getString("ID_BERITA"));
                        model.setJUDUL_BERITA(jsonObject.getString("JUDUL_BERITA"));
                        model.setDESK_BERITA(jsonObject.getString("DESK_BERITA"));
                        model.setTANGGAL(jsonObject.getString("TANGGAL"));
                        model.setPICTURE_BERITA(jsonObject.getString("PICTURE_BERITA"));
                        model.setDATETIME_TGL(jsonObject.getString("DATETIME_TGL"));

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
//
        requestQueue    =   Volley.newRequestQueue(getActivity());
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Berita> lstData) {
        myAdapter       =   new AdapterBerita(getActivity().getApplication(), lstData);
        layoutManager   =   new LinearLayoutManager(getActivity());
//        AdapterBerita recyclerViewAdapter = new AdapterBerita(getContext(), lstData);
//
//        rv_berita.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_berita.setAdapter(recyclerViewAdapter);

        rv_berita.setLayoutManager(layoutManager);
        rv_berita.setAdapter(myAdapter);
//        rv_berita.scrollToPosition(lstData.size()-1);
    }

    public BeritaFragment(){
    }

}
