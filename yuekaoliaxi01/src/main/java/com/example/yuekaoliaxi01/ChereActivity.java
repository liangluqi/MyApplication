package com.example.yuekaoliaxi01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hp on 2017/8/24.
 */

public class ChereActivity extends AppCompatActivity{
   private ImageView Chereimg;
    private  TextView Cheretv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.chere_layout);

        Chereimg= (ImageView) findViewById(R.id.chere_img);
        Cheretv= (TextView) findViewById(R.id.chere_tv);
        Intent intent=getIntent();

        String imgs=intent.getStringExtra("tupian");
        String tvs=intent.getStringExtra("wenzi");

        Cheretv.setText(tvs);
        ImageLoader.getInstance().displayImage(imgs,Chereimg);


    }
}
