package com.example.kiboooo.userdefinedview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kiboooo.userdefinedview.Bean.WeatherImage;
import com.example.kiboooo.userdefinedview.View.ImageBarnnerFrameLayout;
import com.example.kiboooo.userdefinedview.Bean.WeathResult;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ImageBarnnerFrameLayout.ImageBarnnerListener,textdialpg.dialogListener{

//    private ImageBarnnerViewGroup mGroup;
    private ImageBarnnerFrameLayout mGroup;
    private TextView t,t2;
    private ImageView weatherImage;
    private TextView weatherMessage;
    private Gson mGson;

    private final int SUCCESS = 1;
    private final int FALL = 0;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
//                    weatherMessage.setText((String)msg.obj);
                    WeathResult weathResult = mGson.fromJson((String) msg.obj, WeathResult.class);
                    Log.e("weathResult", weathResult.getIsForeign() + "");
                    String image = weathResult.getRealtime().getNowWeather().getInfo();
                    weatherMessage.setText(weathResult.getRealtime().getCity_name()
                            +" "+weathResult.getRealtime().getDate()
                            +" "+weathResult.getRealtime().getNowWeather().getTemperature()
                            +" "+weathResult.getRealtime().getNowWeather().getInfo()
                            +" "+weathResult.getRealtime().getNowWeather().getImg()
                            +" "+weathResult.getLife().getInfo().ganmao.get(1));
                    if (image.equals("晴") || image.equals("晴间多云"))
                    {
                        if (new Date(System.currentTimeMillis()).getHours()>19)
                            image = image+"夜";
                        else
                            image = image+"日";
                    }
                    Glide.with(MainActivity.this).
                            load(WeatherImage.weatherimage.get(image)).into(weatherImage);
                    break;
                case FALL:

                    break;
            }
        }
    };



    //轮播图显示数组；
    private int[] ids = new int[]{
//            R.drawable.first_hanshake,
//            R.drawable.second_hanshake,
//            R.drawable.thirt_hanshake
            R.drawable.first1,
            R.drawable.first2,
            R.drawable.first3

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroup =  findViewById(R.id.image_group);
         t =  findViewById(R.id.textbtn);
        t2 = findViewById(R.id.textbtn2);
        weatherImage = findViewById(R.id.weather);
        weatherMessage = findViewById(R.id.weather_message);
        mGson = new Gson();

        weatherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherRequset.weather("西安", handler, SUCCESS, FALL);
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("textListener", "ghjgjh");
                textdialpg.newInstance().show(getFragmentManager(),"");
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MBean.WITTH = dm.widthPixels;
        List<Bitmap> list = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ids[i]);
            list.add(bitmap);
        }

        mGroup.addBitmaps(list);

//        for (int i = 0; i < ids.length; i++) {
//            ImageView imageView = new ImageView(this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
//            imageView.setImageResource(ids[i]);
//            mGroup.addView(imageView);
//        }
//
        mGroup.setListener(this);
    }


    @Override
    public void ClickListener(int index) {
        Log.e("ClickListener",""+ index);
        Toast.makeText(MainActivity.this, "这是第" + (index + 1) + "图", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogListener(Date BeginDate, Date EndDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        t.setText("开始时间 ："+format.format(BeginDate));
        t2.setText("结束时间 ："+format.format(EndDate));
    }

//    @Override
//    public void onDialogListener(Date da) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//        t.setText(format.format(date));
//    }
}
