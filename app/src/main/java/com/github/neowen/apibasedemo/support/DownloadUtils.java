package com.github.neowen.apibasedemo.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Winson on 2018/7/25.
 */
public class DownloadUtils {

    public interface UpgradeListener {
        void onStart();

        void onFinish();

        void onError();

        void update(int rate);
    }


    public static void downloadFile(final String downloadUrl, final String downloadFilePath, final UpgradeListener upgradeListener) {
        if (upgradeListener != null) {
            upgradeListener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                OutputStream out = null;

                int lastRate = 0;
                try {
                    URL url = new URL(downloadUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    File file = new File(downloadFilePath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdir();
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    out = new FileOutputStream(file);
                    in = urlConnection.getInputStream();
                    float total = urlConnection.getHeaderFieldInt("Content-Length", -1);
                    byte[] buf = new byte[1024];
                    int length = 0;
                    long downloadLength = 0;
                    while ((length = in.read(buf)) != -1) {
                        out.write(buf, 0, length);
                        out.flush();
                        downloadLength += length;
                        int rate = Math.round(100 * downloadLength / total);
                        if (rate != lastRate && upgradeListener != null) {
                            lastRate = rate;
                            upgradeListener.update(lastRate);
                        }
                    }
                    if (upgradeListener != null) {
                        upgradeListener.onFinish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (upgradeListener != null) {
                        upgradeListener.onError();
                    }
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
