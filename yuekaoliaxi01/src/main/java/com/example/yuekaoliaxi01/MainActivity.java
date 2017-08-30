package com.example.yuekaoliaxi01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private Banner banner;
   private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Banner banner= (Banner) findViewById(R.id.banner);
        list = new ArrayList<String>();
        list.add("http://i04.pictn.sogoucdn.com/6a51a4ada9b2139a");
        list.add("http://i03.pictn.sogoucdn.com/2fde20c249375b1f");
        list.add("http://i04.pictn.sogoucdn.com/d9a9ca57baea06ab");
        list.add("http://i02.pic.sogou.com/d784c2ffae96b111");

        banner.setDelayTime(2000);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImages(list);
        banner.setImageLoader(new LunBo());
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position==list.size()-1){
                    Intent intent =new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
    class  LunBo extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);

        }
    }
}
