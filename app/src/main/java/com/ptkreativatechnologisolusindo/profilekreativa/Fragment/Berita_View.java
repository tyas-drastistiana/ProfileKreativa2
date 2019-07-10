package com.ptkreativatechnologisolusindo.profilekreativa.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.FullScreenActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.Login.GantiPasswordActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Login.LoginActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Berita_View extends AppCompatActivity {

    public Bitmap bitmap;
    TextView et_desk, et_judul; ImageView gambar;
    String str_id,str_judul, str_desk, str_tgl, str_picture, str_datetime, FOTO;
    LinkDatabase linkDatabase;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita__view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_berita_view);
        toolbar.setTitle(str_judul);
        setSupportActionBar(toolbar);

        et_desk = (TextView) findViewById(R.id.textView9);
        et_judul = (TextView) findViewById(R.id.TV_vm_vm);
        gambar = (ImageView)findViewById(R.id.imageView3);

        linkDatabase = new LinkDatabase();
        FOTO = new String();
        FOTO = getIntent().getStringExtra("PICTURE_BERITA");


        str_id = getIntent().getStringExtra("id");
        str_judul = getIntent().getStringExtra("judul");
        str_desk = getIntent().getStringExtra("deskripsi");
        str_tgl = getIntent().getStringExtra("tanggal");
        str_picture = getIntent().getStringExtra("picture");
        str_datetime = getIntent().getStringExtra("datetime");

        et_judul.setText(str_judul);
        et_desk.setText(str_desk);

//        gambar.setVisibility(View.VISIBLE);
        Glide.with(this).load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);

//        Picasso.get().load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
//        Picasso.with(this).invalidate(linkDatabase.linkurl()+str_picture);
//        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
//        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).into(gambar);

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Berita_View.this, FullScreenActivity.class);
                intent.putExtra("PICTURE_BERITA", FOTO);
                startActivity(intent);
            }
        });
    }

    public  void onBack(View view){
        finish();
//    public void onBack(View view) {finish();
    }
}
