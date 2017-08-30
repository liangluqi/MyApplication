package com.example.yuekaoliaxi01;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by hp on 2017/8/24.
 */

public class SecondActivity  extends AppCompatActivity implements XListView.IXListViewListener {
  private XListView xlv;
  private List<Bean.ResultBean.DataBean> data;

    private  MyAdapter adapter;
    private  int page=0;
    private  boolean b;
    private SqlUtils utils;
    private  boolean a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        //找到  xlistview
        xlv= (XListView) findViewById(R.id.xlv);
        xlv.setXListViewListener(this);
        xlv.setPullLoadEnable(true);
        //new出数据库工具类
        utils = new SqlUtils(SecondActivity.this);
         // new出 判断网络 类
         a = new PDutils().PanDuan(SecondActivity.this);
          if (!a){
              AlertDialog.Builder builder=new AlertDialog.Builder(SecondActivity.this);
              builder.setMessage("没有网络，请设置");
              builder.setNegativeButton("取消",null);
              builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      Intent inten=new Intent();
                      inten.setAction("android.settings.WIRELESS_SETTINGS");
                      startActivity(inten);
                  }
              }).create().show();

          }
           //item 监听
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ((i-1)<adapter.getCount()){
                    Bean.ResultBean.DataBean bean= (Bean.ResultBean.DataBean) adapter.getItem(i-1);
                    Intent intent=new Intent(SecondActivity.this,ChereActivity.class);
                    intent.putExtra("tupian",bean.getAlbums().get(0));
                    intent.putExtra("wenzi",bean.getImtro());
                    startActivity(intent);
                }

            }
        });
        //加载 数据
        fF("http://apis.juhe.cn/cook/query.php");

    }
    //异步
    private void fF(final String s) {
        new AsyncTask<String,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (a) {
                    if (s != null) {
                        utils.add(s);
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(s, Bean.class);
                        data = bean.getResult().getData();
                        if (adapter == null) {
                            adapter = new MyAdapter(data);
                            xlv.setAdapter(adapter);
                        } else {
                            adapter.TianJia(data, b);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    String czl=utils.cz();
                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(czl, Bean.class);
                    data = bean.getResult().getData();
                    if (adapter == null) {
                        adapter = new MyAdapter(data);
                        xlv.setAdapter(adapter);
                    } else {
                        adapter.TianJia(data, b);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                      String sting=strings[0];
                try {
                    URL url=new URL(sting);
                   HttpURLConnection connect= (HttpURLConnection) url.openConnection();
                    connect.setRequestMethod("POST");
                    connect.setConnectTimeout(5000);
                    connect.setReadTimeout(5000);

                    OutputStream os=connect.getOutputStream();
                    os.write(("key=353369906bcb73c8ee9f0314da13cba4&menu=红烧肉&rn="+page+"&pn=30").getBytes());

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
// 下滑
    @Override
    public void onRefresh() {
         ++page;
        fF("http://apis.juhe.cn/cook/query.php");
        b=true;
        xlv.stopRefresh(true);
    }
//上啦
    @Override
    public void onLoadMore() {
     ++page;
        fF("http://apis.juhe.cn/cook/query.php");
        b=false;
        xlv.stopLoadMore();
    }
   //适配器
    class  MyAdapter extends BaseAdapter{
         public  List<Bean.ResultBean.DataBean> list;
private DisplayImageOptions build;
        public MyAdapter(List<Bean.ResultBean.DataBean> list) {
             this.list = list;
           build = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                   .displayer(new CircleBitmapDisplayer())
                   .build();

         }
         //上啦下滑添加数据
        public  void  TianJia (List<Bean.ResultBean.DataBean> list1 ,boolean c){
            for (Bean.ResultBean.DataBean brb:list1) {
                if (c){
                    list.add(0,brb);
                }else {
                    list.add(brb);
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
        // 多条目类型
        @Override
        public int getViewTypeCount() {
            return 3;
        }
      //多条目加载
        @Override
        public int getItemViewType(int position) {
            if (position%3==0){
                return 0;
            }else if (position%3==1){
                return 1;
            }else  if (position%3==2){
                return 2;
            }
            return 3;
        }

        @Override
         public View getView(int i, View view, ViewGroup viewGroup) {

            int type =getItemViewType(i);
            switch (type){
                case 0:
                    ViewHolder holder;
                    if (view==null){
                        holder=new ViewHolder();
                        view=View.inflate(SecondActivity.this,R.layout.item1,null);
                        holder.tv= (TextView) view.findViewById(R.id.tv);
                        holder.img= (ImageView) view.findViewById(R.id.img);
                        view.setTag(holder);
                    }else {
                        holder= (ViewHolder) view.getTag();
                    }
                         holder.tv.setText(list.get(i).getImtro());
                         ImageLoader.getInstance().displayImage(list.get(i).getAlbums().get(0),holder.img,build);
                    break;

                case 1:
                   ViewHolder1 holder1;
                    if (view==null){
                        holder1=new ViewHolder1();
                        view=View.inflate(SecondActivity.this,R.layout.item2,null);
                        holder1.tv2= (TextView) view.findViewById(R.id.tv);
                        holder1.img2= (ImageView) view.findViewById(R.id.img);
                        view.setTag(holder1);
                    }else {
                        holder1= (ViewHolder1) view.getTag();
                    }
                    holder1.tv2.setText(list.get(i).getImtro());
                    ImageLoader.getInstance().displayImage(list.get(i).getAlbums().get(0),holder1.img2,build);

                    break;

                case 2:
                     ViewHolder3 holder3;
                    if (view==null){
                        holder3 = new ViewHolder3();
                        view=View.inflate(SecondActivity.this,R.layout.item3,null);
                        holder3.tv3= (TextView) view.findViewById(R.id.tv);
                        holder3.img3= (ImageView) view.findViewById(R.id.img);
                        view.setTag(holder3);

                    }else {
                       holder3= (ViewHolder3) view.getTag();
                    }
                    holder3.tv3.setText(list.get(i).getImtro());
                    ImageLoader.getInstance().displayImage(list.get(i).getAlbums().get(0),holder3.img3,build);
                    break;
            }


             return view;
         }
     }
    class  ViewHolder{
        ImageView  img;
        TextView  tv;
    }
    class  ViewHolder1{
        ImageView  img2;
        TextView  tv2;
    }
    class  ViewHolder3{
        ImageView  img3;
        TextView  tv3;
    }

}
