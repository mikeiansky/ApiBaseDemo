package com.github.neowen.apibasedemo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by Winson on 2018/6/30.
 */
public class Utils {

    public static String readLog(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        String result = "";
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(in);
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line + "\r\n";
            }
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void saveLog(final String dir, final String file, final byte[] data) {
//                String path = Environment.getExternalStorageDirectory().getPath();
        File record = new File(dir + File.separator + file);
        try {
            FileOutputStream out = new FileOutputStream(record);
            out.write(data, 0, data.length);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void recordMountedTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getPath();
                File record = new File(path + "/test2.txt");
                try {
                    FileOutputStream out = new FileOutputStream(record);
                    Date date = new Date();
                    out.write(date.toString().getBytes());
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void recordUnmountedTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getPath();
                File record = new File(path + "/test3.txt");
                try {
                    FileOutputStream out = new FileOutputStream(record);
                    Date date = new Date();
                    out.write(date.toString().getBytes());
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void closeIn(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeOut(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean checkAppExists(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
        return launchIntentForPackage != null;
    }

    public static String getVersionName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isNewVersion(String newVersion, String oldVersion) {
        if (newVersion == null) {
            return false;
        }
        if (oldVersion == null) {
            return true;
        }
        return newVersion.compareTo(oldVersion) > 0;
    }

}
