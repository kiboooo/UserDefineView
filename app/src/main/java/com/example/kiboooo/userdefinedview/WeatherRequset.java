package com.example.kiboooo.userdefinedview;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kiboooo on 2017/12/4.
 *
 */

public class WeatherRequset {
    public static final String URL = "http://api.avatardata.cn/Weather/Query";
    public static final String Key = "9a34661c87ba414a9557a337db2f12cc";


    public static void weather(final String City, final Handler handler, final int success, final int fall) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .readTimeout(10, TimeUnit.SECONDS)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("key", Key)
                            .add("cityname", City)
                            .build();

                    Request request = new Request.Builder()
                            .url(URL)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String content = response.body().string();
                        JSONObject object = new JSONObject(content);
                        if (object.getInt("error_code") == 0){
                            Message message = new Message();
                            Log.e("result", object.getString("result"));
                            message.obj = object.getString("result");
//                            message.obj = object;
                            message.what = success;
                            handler.sendMessage(message);
                        }else
                        {
                            int error = object.getInt("error_code");
                            Log.e("error_code", "" + error);
                            handler.sendEmptyMessage(fall);
                        }

                    }else
                        handler.sendEmptyMessage(fall);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(fall);
                }
            }
        }).start();
    }

}
