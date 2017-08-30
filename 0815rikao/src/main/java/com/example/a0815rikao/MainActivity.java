package com.example.a0815rikao;
/*
作者 ： 梁璐琦
作品 ： MainActivity 实现 网络图片的显示
时间 ： 2017/08/15
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
  private   ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        img = (ImageView) findViewById(R.id.img);
    }

        public void but(View view){
        fF("https://img02.sogoucdn.com/net/a/04/link?url=http%3A%2F%2Fimg01.sogoucdn.com%2Fapp%2Fa%2F100520093%2Fca86e620b9e623ff-d72d635343d5bade-bb21a2f5e26f474d714337764c4e8d89.jpg&appid=122");
    }




    private void fF(String s) {
        //异步类
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected void onPostExecute(Bitmap s) {
                super.onPostExecute(s);
           img.setImageBitmap(s);

            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String param=params[0];
                try {
                    URL url= new URL(param);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    int code=connection.getResponseCode();
                    if (code==HttpURLConnection.HTTP_OK){
                        InputStream is=connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                         return bitmap;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;

            }
        }.execute(s);
    }
}
