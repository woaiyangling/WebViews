package com.wzkj.webview;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/13 0013.
 */

public class HttpDownload extends Thread {
    private String murl;
    public HttpDownload(String url){
       this.murl=url;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(murl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (connection.getResponseCode() == 200) {
                //进行读
                InputStream is = connection.getInputStream();
                FileOutputStream fos = null;
                //进行判断到底是否拥有或者支持Sdcard
                //代表是有Sdcard
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //得到Sdcard地址
                    File sdfile = Environment.getExternalStorageDirectory();
                    //第一个参数，要下载的地址   第二个参数：文件取名
                    File f = new File(sdfile, "test.apk");
                    fos = new FileOutputStream(f);
                }
                //边读边写  折中的方法
                byte b[] = new byte[6 * 1024];
                int t;
                int len;
                while ((len = is.read()) != -1) {
                    fos.write(b, 0, len);
                }
                //关闭资源
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                Log.i("tag","下载完毕");
            }
            connection.setConnectTimeout(5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
