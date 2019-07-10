package com.ptkreativatechnologisolusindo.profilekreativa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity {

    LinkDatabase linkDatabase;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        ImageView photoView = (ImageView) findViewById(R.id.photo_view);
        linkDatabase = new LinkDatabase();
        link = new String();
        link = getIntent().getStringExtra("link");
        Glide.with(this).load(linkDatabase.linkurl()+link).placeholder(R.drawable.thumbnail).into(photoView);
    }
}
