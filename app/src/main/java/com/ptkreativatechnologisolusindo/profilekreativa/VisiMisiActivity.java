package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Event.EventActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Fasilitas.FasilitasActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisiMisiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView visi, misi;
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    String str_visi, str_misi;
    public static VisiMisiActivity vm;ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_misi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visimisi);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_visimisi);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_visimisi);
        navigationView.setNavigationItemSelectedListener(VisiMisiActivity.this);



        visi = (TextView)findViewById(R.id.TV_vm_visi);
        misi = (TextView)findViewById(R.id.TV_vm_misi);
        vm = new VisiMisiActivity();
        vm = this;
        str_misi = new String(); str_visi = new String();
        linkDatabase = new LinkDatabase();
        getvisimisi();
    }

    public void getvisimisi() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url      =   linkDatabase.linkurl()+"visimisi.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_visi = jsonObject.getString("ISI_VISI");
                    str_misi = jsonObject.getString("ISI_MISI");

                    visi.setText(str_visi);
                    misi.setText(str_misi);
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

    public void onBack(View view) {
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(VisiMisiActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(GaleryActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(VisiMisiActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(VisiMisiActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(VisiMisiActivity.this,ContactActivity.class);
            startActivity(contact);

        }
        else if (id == R.id.nav_organisasi){
            Intent org = new Intent(VisiMisiActivity.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(VisiMisiActivity.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(VisiMisiActivity.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(VisiMisiActivity.this, EventActivity.class);
            startActivity(event);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_visimisi);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_visimisi);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
