package com.ptkreativatechnologisolusindo.profilekreativa.Event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.ContactActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Event;
import com.ptkreativatechnologisolusindo.profilekreativa.Fasilitas.FasilitasActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.GaleryActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.HomeActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.Organisation;
import com.ptkreativatechnologisolusindo.profilekreativa.ProductActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.VisiMisiActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Adapter.AdapterEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rv_event;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Event> lstData;
    private AdapterEvent myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static EventActivity ma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_event);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_event);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_event);
        navigationView.setNavigationItemSelectedListener(EventActivity.this);


        rv_event = findViewById(R.id.RV_event);
        lstData =   new ArrayList<>();
        linkDatabase = new LinkDatabase();
        ma = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        initDataset();
    }

    private void initDataset() {
        String URL      =   linkDatabase.linkurl()+"event.php?operasi=view_event";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Event model = new Event();
                        model.setID_EVENT(jsonObject.getString("ID_EVENT"));
                        model.setNAMA_EVENT(jsonObject.getString("NAMA_EVENT"));
                        model.setTGL_EVENT(jsonObject.getString("TGL_EVENT"));
                        model.setTEMPAT(jsonObject.getString("TEMPAT"));
                        model.setKAPISITAS(jsonObject.getString("KAPISITAS"));
                        model.setHTM(jsonObject.getString("HTM"));
                        model.setFOTO_EVENT(jsonObject.getString("FOTO_EVENT"));
                        model.setSTATUS(jsonObject.getString("STATUS"));

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
                Log.d("error", error.toString());

                progressDialog.dismiss();
            }
        });
        requestQueue    =   Volley.newRequestQueue(this);
//        ArrayRequest.setShouldCache(false);
        requestQueue.add(ArrayRequest);
    }

    private void setRvadapter(List<Event> lstData) {
        myAdapter       =   new AdapterEvent(this, lstData);
        layoutManager   =   new GridLayoutManager(this,2);
        rv_event.setLayoutManager(layoutManager);
        rv_event.setAdapter(myAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(EventActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(ProductActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(EventActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(EventActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(EventActivity.this,ContactActivity.class);
            startActivity(contact);

        }
        else if (id == R.id.nav_organisasi){
            Intent org = new Intent(EventActivity.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(EventActivity.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(EventActivity.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(EventActivity.this, EventActivity.class);
            startActivity(event);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_event);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_event);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void reload(){
//        for(int i=0; i< lstData.size(); i++){
//            myAdapter.delete(i);
//            lstData.remove(i);
//        }
////        lstData.clear();
//        myAdapter.notifyItemRangeChanged(0, lstData.size());
        lstData.clear();
        myAdapter.notifyDataSetChanged();

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        initDataset();
//        Toast.makeText(getBaseContext(), String.valueOf(lstData.size()), Toast.LENGTH_LONG).show();
    }
}
