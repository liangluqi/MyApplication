package com.example.yuekaoliaxi01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hp on 2017/8/24.
 */

public class JieXi {
    public String JX(InputStream is){

        try {
            ByteArrayOutputStream baso=new ByteArrayOutputStream();
            byte[] buf=new byte[1024];
            int  len=0;

            while((len=is.read(buf))!=-1){
                baso.write(buf,0,len);
            }
            baso.close();
            is.close();
            return  baso.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";

    }
}
