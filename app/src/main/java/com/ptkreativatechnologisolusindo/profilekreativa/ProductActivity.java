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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Product;
import com.ptkreativatechnologisolusindo.profilekreativa.Adapter.AdapterProduct;
import com.ptkreativatechnologisolusindo.profilekreativa.Event.EventActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Fasilitas.FasilitasActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rv_product;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Product> lstData;
    private AdapterProduct myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    LinkDatabase linkDatabase;

    public static ProductActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_product);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_product);
        navigationView.setNavigationItemSelectedListener(ProductActivity.this);


        rv_product = findViewById(R.id.RV_product);
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
        String URL      =   linkDatabase.linkurl()+"product.php?operasi=view_product";
        ArrayRequest    =   new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Product model = new Product();
                        model.setID_PRODUCT(jsonObject.getString("ID_PRODUCT"));
                        model.setDESK_PRODUCT(jsonObject.getString("DESK_PRODUCT"));
                        model.setJUDUL_PRODUCT(jsonObject.getString("JUDUL_PRODUCT"));
                        model.setFOTO_PRODUCT(jsonObject.getString("FOTO_PRODUCT"));
                        model.setTANGGAL_PRODUCT(jsonObject.getString("TANGGAL_PRODUCT"));

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
        requestQueue    =   Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Product> lst) {
        myAdapter       =   new AdapterProduct(ProductActivity.this, lst);
        layoutManager   =   new GridLayoutManager(this, 2);
        rv_product.setLayoutManager(layoutManager);
        rv_product.setAdapter(myAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(ProductActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(ProductActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(ProductActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(ProductActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(ProductActivity.this,ContactActivity.class);
            startActivity(contact);

        }
        else if (id == R.id.nav_organisasi){
            Intent org = new Intent(ProductActivity.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(ProductActivity.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(ProductActivity.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(ProductActivity.this, EventActivity.class);
            startActivity(event);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_product);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_product);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
