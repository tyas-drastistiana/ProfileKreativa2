package com.ptkreativatechnologisolusindo.profilekreativa;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    private int i=0;
    private Timer timer;
    public ProgressBar progressBar;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=(ImageView)findViewById(R.id.logosplash);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgress(0);
        textView = (TextView) findViewById(R.id.text_view);
        textView.setText(" ");

        final long period = 10;
        timer =  new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i<10){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i)+"%");
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    timer.cancel();
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },0,period);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(SplashScreen.this,HomeActivity.class);
//                startActivity(i);
//                finish();
//            }
//        },splashTimeOut);

//        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
//        logo.startAnimation(myanim);

//        progressBar.setMax(100);
//        progressBar.setScaleY(3f);
//        progressAnimation();
    }

}
