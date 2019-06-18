package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import uk.co.senab.photoview.PhotoViewAttacher;

public class Organisation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int IMG_REQUEST =1;
    public Bitmap bitmap;
    ImageButton organisasi;
    LinkDatabase linkDatabase;
    private RequestQueue requestQueue;
    private JsonArrayRequest arrayRequest;
    String url_organisasi; ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_org);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_org);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_org);
        navigationView.setNavigationItemSelectedListener(Organisation.this);


        organisasi = (ImageButton) findViewById(R.id.IV_organisasi);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(organisasi);
        photoViewAttacher.update();
        url_organisasi = new String();
        linkDatabase = new LinkDatabase();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        geturl();
    }

    private void geturl() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_organisasi";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    url_organisasi = linkDatabase.linkurl()+jsonObject.getString("URL_STRUKTUR_ORGANISASI");
//                    Toast.makeText(getBaseContext(), url_img.toString(), Toast.LENGTH_LONG).show();
                    Picasso.with(getBaseContext()).load(url_organisasi).into(organisasi);

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

    public void onBack(View view) {finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                organisasi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(Organisation.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(GaleryActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(Organisation.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(Organisation.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(Organisation.this,ContactActivity.class);
            startActivity(contact);

        }
        else if (id == R.id.nav_organisasi){
            Intent org = new Intent(Organisation.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(Organisation.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(Organisation.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(Organisation.this, EventActivity.class);
            startActivity(event);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_org);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_org);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
