package com.github.neowen.apibasedemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Winson on 2018/8/3.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    Context context;
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    /** infos */
    private Map<String, String> infos = new HashMap<String, String>();

    public CrashHandler(Context context) {
        this.context = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        ex.printStackTrace();

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry: infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        Utils.saveLog(context.getCacheDir().getPath(), "crash_log.txt", sb.toString().getBytes());


        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(t, ex);
        }
    }


}
