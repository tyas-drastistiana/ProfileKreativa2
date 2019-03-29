package com.ptkreativatechnologisolusindo.profilekreativa;

import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Galery_View extends AppCompatActivity {

    public Bitmap bitmap;
    TextView et_desk; ImageView gambar;
    String str_id, str_desk, str_tgl, str_picture, time2, time;
    LinkDatabase linkDatabase;
    boolean status=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery__view);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_galery_view);
//        setSupportActionBar(toolbar);

        et_desk = (TextView) findViewById(R.id.txt_desk_view_galery);
        gambar = (ImageView)findViewById(R.id.IV_gelery_view);

//        str_id = new String();str_desk= new String();str_tgl=new String();str_picture=new String();time=new String();
//        time2=new String();
        linkDatabase = new LinkDatabase();

        str_id = getIntent().getStringExtra("ID_GALLERY");
        str_desk = getIntent().getStringExtra("DESK_GALLERY");
        str_picture = getIntent().getStringExtra("FOTO_GALLERY");
        str_tgl = getIntent().getStringExtra("TANGGAL_GALERY");

        et_desk.setText(str_desk);
//        gambar.setVisibility(View.VISIBLE);
//        Glide.with(this).load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
//        Picasso.get().load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
        Picasso.with(this).invalidate(linkDatabase.linkurl()+str_picture);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).into(gambar);
    }

    public void onBack(View view) {finish();
    }
}
