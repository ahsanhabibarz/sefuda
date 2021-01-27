package com.example.habii.sefuda;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView,rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        imageView = (ImageView)findViewById(R.id.loading);
        rotate = (ImageView)findViewById(R.id.sifatullah);

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rotate.animate().rotation(rotate.getRotation()+360).start();

            }
        });


        Glide.with(getApplicationContext()).load(R.drawable.loading_ani).into(imageView);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //progressDialog.dismiss();
                finish();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }, 3000);


    }
}
