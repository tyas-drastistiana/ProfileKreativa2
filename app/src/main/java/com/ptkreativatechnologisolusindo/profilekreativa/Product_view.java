package com.ptkreativatechnologisolusindo.profilekreativa;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Product_view extends AppCompatActivity {

    public Bitmap bitmap;
    TextView et_desk, et_judul; ImageView gambar;
    String str_id,str_judul, str_desk, str_tgl, str_picture, time;
    LinkDatabase linkDatabase;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product_view);
//        setSupportActionBar(toolbar);

        et_desk = (TextView) findViewById(R.id.TIL_eprodut_desk);
        et_judul = (TextView) findViewById(R.id.TIL_eprodut_judul);
        gambar = (ImageView)findViewById(R.id.IV_eprodut_gambar);

        linkDatabase = new LinkDatabase();


        str_id = getIntent().getStringExtra("ID_PRODUCT");
        str_judul = getIntent().getStringExtra("JUDUL_PRODUCT");
        str_desk = getIntent().getStringExtra("DESK_PRODUCT");
        str_picture = getIntent().getStringExtra("FOTO_PRODUCT");
        str_tgl = getIntent().getStringExtra("TANGGAL_PRODUCT");

        et_judul.setText(str_judul);
        et_desk.setText(str_desk);

//        Picasso.get().load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
        Picasso.with(this).invalidate(linkDatabase.linkurl()+str_picture);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).into(gambar);
    }

    public  void onBack(View view){
        finish();
//    public void onBack(View view) {finish();
    }

}
