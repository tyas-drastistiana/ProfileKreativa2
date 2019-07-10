package com.ptkreativatechnologisolusindo.profilekreativa.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Portofolio;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.Adapter.AdapterPortofolio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PortofolioFragment extends Fragment {

    private static final Object TAG = "RecyclerViewFragment";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private RecyclerView rv_portofolio;
    private List<Portofolio> lstData;
    private AdapterPortofolio myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    ProgressDialog progressDialog;
    LinkDatabase linkDatabase;
    Context mcontext;


    public static PortofolioFragment ma;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initDataset();
    }

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"portofolio.php?operasi=view_portofolio";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Portofolio model = new Portofolio();
                        model.setID_PORTOFOLIO(jsonObject.getString("ID_PORTOFOLIO"));
                        model.setNAMA_PROJECT(jsonObject.getString("NAMA_PROJECT"));
                        model.setTANGGAL_PROJECT(jsonObject.getString("TANGGAL_PROJECT"));
                        model.setTEMPAT_PROJECT(jsonObject.getString("TEMPAT_PROJECT"));
                        model.setDESKRIPSI_PROJECT(jsonObject.getString("DESKRIPSI_PROJECT"));
                        model.setFOTO_PROJECT(jsonObject.getString("FOTO_PROJECT"));

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
        requestQueue    =   Volley.newRequestQueue(getActivity());
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Portofolio> lst) {
        myAdapter       =   new AdapterPortofolio(getActivity(), lst);
        layoutManager   =   new LinearLayoutManager(getActivity());
//        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        rv_portofolio.setLayoutManager(layoutManager);
        rv_portofolio.setAdapter(myAdapter);
//        rv_portofolio.setAdapter(new AdapterPortofolio(portofolios));
//        rv_portofolio.scrollToPosition(lst.size()-1);
        rv_portofolio.addItemDecoration(new DividerItemDecoration(getActivity(),0));
//        rv_portofolio.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public PortofolioFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_portofolio, container, false);

//        initDataset().setTitle(PortofolioFragment.class.getSimpleName());
        rv_portofolio = (RecyclerView) v.findViewById(R.id.recyclerview_portofolio);
//        rv_portofolio.addItemDecoration(new DividerItemDecoration(getActivity(),0));
//        rv_portofolio.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
//        return inflater.inflate(R.layout.fragment_portofolio, container, false);


    }

}



