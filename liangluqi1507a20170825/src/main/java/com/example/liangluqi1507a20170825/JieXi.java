package com.example.liangluqi1507a20170825;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：梁璐琦
 * 作品：JieXi    解析类
 * Created by hp on 2017/8/25.
 */

public class JieXi {
    public String JX (InputStream is){

        try {
            ByteArrayOutputStream baso=new ByteArrayOutputStream();
            byte[] buf=new byte[1024];
            int  len=0;
            while ((len=is.read(buf))!=-1){
                baso.write(buf,0,len);
            }
            baso.close();
            is.close();
            System.out.println(baso.toString());
            return baso.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "" ;
    }
}
