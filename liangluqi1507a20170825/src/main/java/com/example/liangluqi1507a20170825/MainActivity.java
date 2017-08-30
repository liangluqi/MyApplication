package com.example.liangluqi1507a20170825;
/*
作者:梁璐琦
作品:MainActivity  本了实现了  无线轮播 上拉下滑  还有多条目加载等；
时间： 2017/08/25
 */
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private Banner banner;
    private  XListView xlv;
    private List<Bean.DataBean> data;
    private MyAdapter adapter;
    private  ArrayList<String> arrayList;
    private boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            //找到控件
       banner= (Banner) findViewById(R.id.banner);
        xlv= (XListView) findViewById(R.id.xlv);
        xlv.setXListViewListener(this);
        xlv.setPullLoadEnable(true);
        arrayList = new ArrayList<String>();
        arrayList.add("http://uimg.quanmin.tv/1503638052/61152.jpg");
        arrayList.add("http://snap.quanmin.tv/263925-1503642118-369.jpg");
        arrayList.add("http://image.quanmin.tv/fb5324cc0e9a65ca090fe4e379e130fbjpg");
        arrayList.add("http://uimg.quanmin.tv/1503637943/32b38.jpg");

        //banner 的 相关方法
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//图片类型
        banner.setDelayTime(2000);//时间间隔
        banner.setImages(arrayList);//加载图片
        banner.setImageLoader(new LunBo());//实现轮播
        banner.start();//启动
        //加载网页数据
        fF("http://www.quanmin.tv/json/categories/lol/list.json");
    }
    //异步
    private void fF(String s) {
        new AsyncTask<String,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null){
                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(s, Bean.class);
                    data=bean.getData();
                    System.out.println(data);
                    //判断adapter是否为空
                    if (adapter==null){
                        adapter = new MyAdapter(data);
                        xlv.setAdapter(adapter);
                    }else {
                        adapter.Tianjia(data,b);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            protected String doInBackground(String... strings) {
            String string=strings[0];
                try {
                    //网络请求
                    URL url=new URL(string);
                    HttpURLConnection connect= (HttpURLConnection) url.openConnection();
                    //GET请求
                    connect.setRequestMethod("GET");
                    connect.setReadTimeout(5000);
                    connect.setConnectTimeout(5000);
                    int code=connect.getResponseCode();
                    if (code==HttpURLConnection.HTTP_OK){
                        InputStream is=connect.getInputStream();
                        String jx=new JieXi().JX(is);
                        return  jx;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(s);
    }
 //下拉
    @Override
    public void onRefresh() {
        fF("http://www.quanmin.tv/json/categories/lol/list.json");
        b=true;
        xlv.stopRefresh(true);

    }
//上滑
    @Override
    public void onLoadMore() {
        fF("http://www.quanmin.tv/json/categories/lol/list.json");
        b=false;
        xlv.stopLoadMore();
    }
    //适配器
    class MyAdapter extends BaseAdapter{
        private  List<Bean.DataBean> list;
        private   DisplayImageOptions op;
        public MyAdapter(List<Bean.DataBean> list) {
            this.list = list;
             op=new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).build();
        }
           //添加数据
         public  void Tianjia(List<Bean.DataBean> list1,boolean c){
             for (Bean.DataBean bdb:list1) {
                  if (c){
                      list.add(bdb);
                  }else {
                      list.add(bdb);
                  }
             }
         }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int i) {
            return list.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
       //多条目类型数量；
        @Override
        public int getViewTypeCount() {
            return 3;
        }
         //多条目类型；
        @Override
        public int getItemViewType(int position) {
            if (position%3==0){
                return 0;
            }else if (position%3==1){
                return 1;
            }else if (position%3==2){
                return 2;
            }
            return 3;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //自定义一个type值
            int type=getItemViewType(i);
            switch (type){
                case  0:
                    ViewHolder holder;
                    if (view==null){
                        holder=new ViewHolder();
                        view=View.inflate(MainActivity.this,R.layout.item,null);
                        holder.img= (ImageView) view.findViewById(R.id.img);
                        holder.tv= (TextView) view.findViewById(R.id.tv);
                        holder.tv1= (TextView) view.findViewById(R.id.tv1);
                        view.setTag(holder);
                    }else {
                        holder= (ViewHolder) view.getTag();
                    }
                    holder.tv.setText(list.get(i).getTitle());
                    holder.tv1.setText(list.get(i).getNick());
                    ImageLoader.getInstance().displayImage(list.get(i).getApp_shuffling_image(),holder.img,op);
                    break;
                case 1:
                    ViewHolder2 holder2;
                    if (view==null){
                        holder2=new ViewHolder2();
                        view=View.inflate(MainActivity.this,R.layout.item2,null);
                        holder2.img2= (ImageView) view.findViewById(R.id.img);
                        holder2.tv2= (TextView) view.findViewById(R.id.tv);
                        holder2.tv12= (TextView) view.findViewById(R.id.tv1);
                        view.setTag(holder2);
                    }else {
                        holder2= (ViewHolder2) view.getTag();
                    }
                    holder2.tv2.setText(list.get(i).getTitle());
                    holder2.tv12.setText(list.get(i).getNick());
                    ImageLoader.getInstance().displayImage(list.get(i).getApp_shuffling_image(),holder2.img2,op);
                    break;
                case 2:
                    ViewHolder3 holder3;
                    if (view==null){
                        holder3=new ViewHolder3();
                        view=View.inflate(MainActivity.this,R.layout.item3,null);
                        holder3.img3= (ImageView) view.findViewById(R.id.img);
                        holder3.tv3= (TextView) view.findViewById(R.id.tv);
                        holder3.tv13= (TextView) view.findViewById(R.id.tv1);
                        view.setTag(holder3);
                    }else {
                        holder3= (ViewHolder3) view.getTag();
                    }
                    holder3.tv3.setText(list.get(i).getTitle());
                    holder3.tv13.setText(list.get(i).getNick());
                    ImageLoader.getInstance().displayImage(list.get(i).getApp_shuffling_image(),holder3.img3,op);
                    break;
            }
            return view;
        }
    }
    //优化
    class ViewHolder{
        ImageView  img;
        TextView   tv;
        TextView   tv1;
    }
    class ViewHolder2{
        ImageView  img2;
        TextView   tv2;
        TextView   tv12;
    }class ViewHolder3{
        ImageView  img3;
        TextView   tv3;
        TextView   tv13;
    }
        // Lunbo 加载图片数据；
    class  LunBo extends com.youth.banner.loader.ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
