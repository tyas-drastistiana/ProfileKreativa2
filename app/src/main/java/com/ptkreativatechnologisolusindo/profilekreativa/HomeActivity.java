package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Peserta;
import com.ptkreativatechnologisolusindo.profilekreativa.Fragment.BeritaFragment;
import com.ptkreativatechnologisolusindo.profilekreativa.Fragment.HomeFragment;
import com.ptkreativatechnologisolusindo.profilekreativa.Fragment.PortofolioFragment;
import com.ptkreativatechnologisolusindo.profilekreativa.Tab.MyAdapter;
import com.ptkreativatechnologisolusindo.profilekreativa.Tab.SlidingTabLayout;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private SlidingTabLayout mSlidingTabLayout;;
//    private ViewPager mViewPager;
// private BottomBar bottomBar;

//    private AccountHeader.Result headerNavigationleft;

//    private RelativeLayout button;
    ImageView imageView;TextView username;
    String EMAIL_PESERTA;
    LinkDatabase linkDatabase;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Peserta> lstData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        username = findViewById(R.id.username);

        linkDatabase = new LinkDatabase();

        getViewEvent();


//        button = (RelativeLayout)findViewById(R.id.button);

//        mViewPager = (ViewPager)findViewById(R.id.vp_tabs);
//        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), this));
//
//        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.st1_tabs);
//        mSlidingTabLayout.setDistributeEvenly(true);
//        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
//        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
//        mSlidingTabLayout.setViewPager(mViewPager);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer ,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //=======================================

//            headerNavigationleft = new AccountHeader()
//                    .withActivity(this)
//                    .withCompactStyle(false)
//                    .withSavedInstance(savedInstanceState)
//                    .withHeaderBackground(R.drawable.side_nav_bar)
//                    .addProfiles(
//                            new ProfileDrawerItem().withName("Tyas").withEmail("kampus.com").withIcon(getResources().getDrawable(R.drawable.logo))
//                    )
//                    .build();

//            HomeFragment fragment = new HomeFragment();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction()
//                    .replace(R.id.main, fragment)
//                    .addToBackStack(null)
//                    .commit();
        }
    }

    private void getViewEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url      =   linkDatabase.linkurl()+"peserta_event.php?operasi=view_peserta";
//        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    JSONObject jsonObject = response.getJSONObject(0);
////                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
////                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
//                    ID_EVENT = jsonObject.getString("ID_EVENT");
//                    NAMA_EVENT = jsonObject.getString("NAMA_EVENT");
//                    TGL_EVENT = jsonObject.getString("TGL_EVENT");
//                    TEMPAT = jsonObject.getString("TEMPAT");
//                    KAPISITAS = jsonObject.getString("KAPISITAS");
//                    HTM = jsonObject.getString("HTM");
//                    FOTO_EVENT = jsonObject.getString("FOTO_EVENT");
//                    STATUS = jsonObject.getString("STATUS");
//
//                    gambar.setVisibility(View.VISIBLE);
//                    Glide.with(EventViewActivity.this).load(linkDatabase.linkurl()+FOTO_EVENT).placeholder(R.drawable.thumbnail).into(gambar);
//
//                    nama.setText(NAMA_EVENT);
//                    tgl.setText(TGL_EVENT);
//                    tempat.setText(TEMPAT);
//                    status_aktif.setText(STATUS);
//                    kapasitas.setText(KAPISITAS);
//                    htm.setText(HTM);
//                    progressDialog.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.toString());
//            }
//        }
//        );
//        requestQueue    =   Volley.newRequestQueue(this);
//        requestQueue.add(arrayRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home= new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(home);
        }
//        else if (id == R.id.nav_org) {
//            Intent org= new Intent(HomeActivity.this,MenuProfileActivity.class);
//            startActivity(org);
//        }
        else if (id == R.id.nav_galery) {
            Intent galery= new Intent(HomeActivity.this,GaleryActivity.class);
            startActivity(galery);

        } else if (id == R.id.nav_product) {
            Intent product= new Intent(HomeActivity.this,ProductActivity.class);
            startActivity(product);

        } else if (id == R.id.nav_contact) {
            Intent contact= new Intent(HomeActivity.this,ContactActivity.class);
            startActivity(contact);

        }else if (id == R.id.nav_organisasi){
            Intent org = new Intent(HomeActivity.this, Organisation.class);
            startActivity(org);
        }else if (id == R.id.nav_visimisi){
            Intent visimisi = new Intent(HomeActivity.this, VisiMisiActivity.class);
            startActivity(visimisi);
        }else if (id == R.id.nav_fasilitas){
            Intent fasilitas = new Intent(HomeActivity.this, FasilitasActivity.class);
            startActivity(fasilitas);
        }else if (id == R.id.nav_event){
            Intent event = new Intent(HomeActivity.this, EventActivity.class);
            startActivity(event);
        }else if (id == R.id.nav_profile){
            Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(profile);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.action_settings:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_Up_date:
                            selectedFragment = new BeritaFragment();
                            break;
                        case R.id.action_potofolio:
                            selectedFragment = new PortofolioFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }

    };
}
