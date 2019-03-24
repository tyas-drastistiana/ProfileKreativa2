package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Galery;
import com.ptkreativatechnologisolusindo.profilekreativa.adapter.AdapterGalery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GaleryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rv_galery;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Galery> lstData;
    private AdapterGalery myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static GaleryActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_galery);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_galery);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_galery);
        navigationView.setNavigationItemSelectedListener(GaleryActivity.this);



        rv_galery = findViewById(R.id.RV_galery);
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
        String URL      =   linkDatabase.linkurl()+"galery.php?operasi=view_galery";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Galery model = new Galery();
                        model.setID_GALLERY(jsonObject.getString("ID_GALLERY"));
                        model.setDESK_GALLERY(jsonObject.getString("DESK_GALLERY"));
                        model.setFOTO_GALLERY(jsonObject.getString("FOTO_GALLERY"));
                        model.setTANGGAL_GALERY(jsonObject.getString("TANGGAL_GALERY"));

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
        requestQueue    =   Volley.newRequestQueue(GaleryActivity.this);
        requestQueue.add(ArrayRequest);
    }


    public void setRvadapter(List<Galery> lst) {
        myAdapter       =   new AdapterGalery(GaleryActivity.this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
        rv_galery.setLayoutManager(layoutManager);
        rv_galery.setAdapter(myAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(GaleryActivity.this,HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_org) {
            Intent org= new Intent(GaleryActivity.this,MenuProfileActivity.class);
            startActivity(org);

        } else if (id == R.id.nav_galery) {
            Intent galery= new Intent(GaleryActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(GaleryActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(GaleryActivity.this,ContactActivity.class);
            startActivity(contact);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_galery);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_galery);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
