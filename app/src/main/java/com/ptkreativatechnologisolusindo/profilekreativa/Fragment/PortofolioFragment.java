package com.ptkreativatechnologisolusindo.profilekreativa.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Berita;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Portofolio;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.adapter.AdapterBerita;
import com.ptkreativatechnologisolusindo.profilekreativa.adapter.AdapterPortofolio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
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

    LinkDatabase linkDatabase;
    Context mcontext;


    public static PortofolioFragment ma;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;
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
//                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.toString()); progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(getActivity());
        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Portofolio> lst) {
        myAdapter       =   new AdapterPortofolio(getActivity(), lst);
        layoutManager   =   new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        rv_portofolio.setLayoutManager(layoutManager);
        rv_portofolio.setAdapter(myAdapter);
//        rv_portofolio.setAdapter(new AdapterPortofolio(portofolios));
        rv_portofolio.scrollToPosition(lst.size()-1);
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
//
//        final List<Portofolio> portofolios = new ArrayList<>();
//        portofolios.add(new Portofolio("0 Atanasoff John Vincent",
//                "John V. Atanasoff is considered by many historians to be" +
//                        "the inventor of the modern electronic computer. He was" +
//                        "born October 4, 1903, in Hamilton, New York. As a young" +
//                        "man, Atanasoff showed considerable interest in and a talent" +
//                        "for electronics. His academic background (B.S. in electrical" +
//                        "engineering, Florida State University, 1925; m.S. in mathematics, Iowa State College, 1926; and Ph.D. in experimental" +
//                        "physics, University of Wisconsin, 1930) well equipped him" +
//                        "for the design of computing devices.  "));
//        portofolios.add(new Portofolio("7 Bezos, Jeffrey P.",
//                " With its ability to display extensive information and interact" +
//                        "with users, the World Wide Web of the mid-1990s clearly" +
//                        "had commercial possibilities. But it was far from clear how" +
//                        "traditional merchandising could be adapted to the online" +
//                        "world, and how the strengths of the new medium could be" +
//                        "translated into business advantages. In creating Amazon." +
//                        "com, “the world’s largest bookstore,” Jeff Bezos would show" +
//                        "how the Web could be used to deliver books and other merchandise to millions of consumers. "));
//
//        rv_portofolio.setAdapter(new AdapterPortofolio(portofolios));

        return v;
//        return inflater.inflate(R.layout.fragment_portofolio, container, false);


    }

}



