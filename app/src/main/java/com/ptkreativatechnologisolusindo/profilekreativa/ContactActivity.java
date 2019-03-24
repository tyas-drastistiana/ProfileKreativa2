package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Profil> lstData;
    public static ContactActivity PA;
    TextView nama_perusahaan, alamat, no_telp, email,instagram, wa;
    String str_nama_perusahaan, str_alamat, str_no_telp, str_email,str_instagram,
            str_desk, id, str_wa;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_contact);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_contact);
        navigationView.setNavigationItemSelectedListener(ContactActivity.this);

        linkDatabase = new LinkDatabase();
        lstData = new ArrayList<>();
        str_nama_perusahaan = new String();
        str_alamat = new String();
        str_no_telp = new String();
        str_email = new String();
        str_instagram = new String();
        id = new String();
        str_wa = new String();
        nama_perusahaan = (TextView)findViewById(R.id.txt_contact_nama);
        alamat = (TextView)findViewById(R.id.txt_contact_almt);
        no_telp = (TextView)findViewById(R.id.txt_contact_telp);
        email = (TextView)findViewById(R.id.txt_contact_email);
        instagram = (TextView)findViewById(R.id.txt_contact_instagram);
        wa = (TextView)findViewById(R.id.txt_contact_wa);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        PA = this;

        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    str_email = jsonObject.getString("EMAIL");
                    str_no_telp = jsonObject.getString("TELP");
                    str_alamat = jsonObject.getString("ALAMAT");
                    str_instagram = jsonObject.getString("INSTAGRAM");
                    str_wa =jsonObject.getString("WHATSAPP");


                    nama_perusahaan.setText(str_nama_perusahaan);
                    alamat.setText(str_alamat);
                    no_telp.setText(str_no_telp);
                    email.setText(str_email);
                    instagram.setText(str_instagram);
                    wa.setText(str_wa);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(ContactActivity.this,HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_org) {
            Intent org= new Intent(ContactActivity.this,MenuProfileActivity.class);
            startActivity(org);

        } else if (id == R.id.nav_galery) {
            Intent galery= new Intent(ContactActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(ContactActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(ContactActivity.this,ContactActivity.class);
            startActivity(contact);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_contact);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_contact);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onEmail(View view) {
        Intent intent =new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",str_email, null));
//        intent.putExtra(Intent.EXTRA_EMAIL, "fatoni.noah@gmail.com");
//        intent.setType("message/rfc822");
//        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "choose an email client"));
    }

    public void onWa(View view) {
        try {
            String text = "";// Replace with your message.

            String toNumber = "62895631517250"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+str_wa +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
