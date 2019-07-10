package com.ptkreativatechnologisolusindo.profilekreativa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptkreativatechnologisolusindo.profilekreativa.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private ImageView ivkreativa;
    private TextView tvkreativa, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivkreativa = (ImageView) findViewById(R.id.ivkreativa);
        nama = (TextView) findViewById(R.id.txtkreativa1);
        tvkreativa = (TextView) findViewById(R.id.txtkreativa2);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        ivkreativa.startAnimation(myanim);
        nama.startAnimation(myanim);
        tvkreativa.startAnimation(myanim);

        final Intent i = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

}
