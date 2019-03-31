package com.ptkreativatechnologisolusindo.profilekreativa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class MenuProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_org);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_org);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_org);
        navigationView.setNavigationItemSelectedListener(MenuProfileActivity.this);

    }

    public void onBack(View view) {
        finish();
    }

    public void onProfile(View view) {
        Intent intent = new Intent(this, CompanyProfileActivity.class);
        startActivity(intent);
    }

    public void onStruktur(View view) {
        Intent intent = new Intent(this, Organisation.class);
        startActivity(intent);
    }


    public void onVisiMisi(View view) {
        Intent intent = new Intent(this, VisiMisiActivity.class);
        startActivity(intent);
    }

    public void onFasilitas(View view) {
        Intent intent = new Intent(this, FasilitasActivity.class);
        startActivity(intent);
    }

    private String ImagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,  byteArrayOutputStream);
        byte [] imgbytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbytes, Base64.DEFAULT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(MenuProfileActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(MenuProfileActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(MenuProfileActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(MenuProfileActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(MenuProfileActivity.this,ContactActivity.class);
            startActivity(contact);

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
