package com.talkover;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.talkover.api.TemplateApi;
import com.talkover.model.AppEntity;
import com.talkover.model.TemplateEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public class MyApplication extends Application {

    private static final String DB_FILE = "template.bin";
    public static TemplateApi sTemplateApi;
    public static Map<String, AppEntity> sDatabase;
    public static LinkedList<String> sApp;


    public static void insert(AppEntity template, Context context) {
        // sDatabase.addFirst(template);

        String app = template.getApp();
        sDatabase.put(app, template);
        if (!sApp.contains(template.getApp())) {
            // for index
            sApp.addFirst(app);
        }
        flush(context);
    }

    private static void flush(Context context) {
        Gson gson = new Gson();
        try (PrintWriter writer = new PrintWriter(context.openFileOutput(DB_FILE, Context.MODE_PRIVATE))) {
            for (String it : sApp) {
                AppEntity entity = sDatabase.get(it);
                String json = gson.toJson(entity);
                writer.println(json);
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // retrofit
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://47.100.4.37").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        sTemplateApi = retrofit.create(TemplateApi.class);


        // init db
        sDatabase = new HashMap<>();
        sApp = new LinkedList<>();

        

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(DB_FILE)))) {
            Gson gson = new Gson();
            while (true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                AppEntity it = gson.fromJson(line, AppEntity.class);
                sDatabase.put(it.getApp(), it);
                sApp.add(it.getApp());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        sApp.add("饿了么");
//        sApp.add("Wechat");
//        sApp.add("QQ游戏");

    }

}
