package com.ptkreativatechnologisolusindo.profilekreativa.Fasilitas;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Adapter.AdapterFasilitas;
import com.ptkreativatechnologisolusindo.profilekreativa.ContactActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Fasilitas;
import com.ptkreativatechnologisolusindo.profilekreativa.Event.EventActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.GaleryActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.HomeActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.Organisation;
import com.ptkreativatechnologisolusindo.profilekreativa.ProductActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.VisiMisiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FasilitasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fasilitas);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_fasilitas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_fasilitas);
        navigationView.setNavigationItemSelectedListener(FasilitasActivity.this);



//        final SwipeRefreshLayout swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.swipelayout);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.color1Accent);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                        initDataset();
//
//                    }
//
//                });
//            }
//        });



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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_fasilitas);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(FasilitasActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(GaleryActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(FasilitasActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(FasilitasActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(FasilitasActivity.this,ContactActivity.class);
            startActivity(contact);
        }
        else if (id == R.id.nav_organisasi){
            Intent org = new Intent(FasilitasActivity.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(FasilitasActivity.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(FasilitasActivity.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(FasilitasActivity.this, EventActivity.class);
            startActivity(event);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_fasilitas);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
