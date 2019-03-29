package com.ptkreativatechnologisolusindo.profilekreativa;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Fasilitas_View extends AppCompatActivity {

    public Bitmap bitmap;
    TextView et_desk; ImageView gambar;
    String str_id, str_desk, str_tgl, str_picture, time2, time;
    LinkDatabase linkDatabase;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas__view);

        et_desk = (TextView) findViewById(R.id.txt_desk_view_fasilitas);
        gambar = (ImageView)findViewById(R.id.IV_fasilitas_view);

        linkDatabase = new LinkDatabase();
        str_id = getIntent().getStringExtra("ID_FASILITAS");
        str_desk = getIntent().getStringExtra("DESK_FASILITAS");
        str_picture = getIntent().getStringExtra("FOTO_FASILITAS");
        str_tgl = getIntent().getStringExtra("TANGGAL_FASILITAS");

        et_desk.setText(str_desk);
//        gambar.setVisibility(View.VISIBLE);
//        Glide.with(this).load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
//        Picasso.get().load(linkDatabase.linkurl()+str_picture).placeholder(R.drawable.thumbnail).into(gambar);
        Picasso.with(this).invalidate(linkDatabase.linkurl()+str_picture);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(this).load(linkDatabase.linkurl()+str_picture).into(gambar);
    }

    public void onBatal(View view) {finish();
    }
}
