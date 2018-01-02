package com.example.kiboooo.userdefinedview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.kiboooo.userdefinedview.Bean.WeathResult;
import com.example.kiboooo.userdefinedview.Bean.WeatherImage;
import com.example.kiboooo.userdefinedview.View.ImageBarnnerFrameLayout;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ImageBarnnerFrameLayout.ImageBarnnerListener, textdialpg.dialogListener {


    private ImageBarnnerFrameLayout mGroup;
    private TextView t, t2;
    private ImageView weatherImage;
    private TextView weatherMessage;
    private Gson mGson;

    private LocationManager mLocationManager;
    private LocationClient locationClient;


    private final int SUCCESS = 1;
    private final int FALL = 0;

    private final int GETLOCATION_SUCCESS = 11;
    private final int GETLOCATION_FALL = 10;

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
                            + " " + weathResult.getRealtime().getDate()
                            + " " + weathResult.getRealtime().getNowWeather().getTemperature()
                            + " " + weathResult.getRealtime().getNowWeather().getInfo()
                            + " " + weathResult.getRealtime().getNowWeather().getImg()
                            + " " + weathResult.getLife().getInfo().ganmao.get(1));
                    if (image.equals("晴") || image.equals("晴间多云")) {
                        if (new Date(System.currentTimeMillis()).getHours() > 19)
                            image = image + "夜";
                        else
                            image = image + "日";
                    }
                    Glide.with(MainActivity.this).
                            load(WeatherImage.weatherimage.get(image)).into(weatherImage);
                    break;

                case GETLOCATION_SUCCESS:
                    String LocationName = (String) msg.obj;
                    WeatherRequset.weather(LocationName, handler, SUCCESS, FALL);
                    break;
                case GETLOCATION_FALL:
                    Toast.makeText(MainActivity.this, "获取地名出错", Toast.LENGTH_SHORT).show();
                    break;
                case FALL:
                    Toast.makeText(MainActivity.this, "获取天气预报出错", Toast.LENGTH_SHORT).show();
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
        mGroup = findViewById(R.id.image_group);
        t = findViewById(R.id.textbtn);
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
                textdialpg.newInstance().show(getFragmentManager(), "");
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
        CheckPermission();
    }


    @Override
    public void ClickListener(int index) {
        Log.e("ClickListener", "" + index);
        Toast.makeText(MainActivity.this, "这是第" + (index + 1) + "图", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogListener(Date BeginDate, Date EndDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        t.setText("开始时间 ：" + format.format(BeginDate));
        t2.setText("结束时间 ：" + format.format(EndDate));
    }

//    @Override
//    public void onDialogListener(Date da) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//        t.setText(format.format(date));
//    }

    private void iniJW() {
        /*调用系统 location 服务*/
//        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        String provider = null;
//        List<String> list = mLocationManager.getProviders(true);
//        if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//            Toast.makeText(MainActivity.this, "网络", Toast.LENGTH_SHORT).show();
//            provider = LocationManager.NETWORK_PROVIDER;
//        } else if (list.contains(LocationManager.GPS_PROVIDER)) {
//            Toast.makeText(MainActivity.this, "GPS", Toast.LENGTH_SHORT).show();
//            provider = LocationManager.GPS_PROVIDER;
//        }else {
//            Toast.makeText(MainActivity.this, "网络或者GPS没有打开", Toast.LENGTH_SHORT).show();
//        }
//
//        Location location = mLocationManager.getLastKnownLocation(provider);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//获取当前位置的详细地址信息
        locationClient.setLocOption(option);
        locationClient.start();


//        String LongitudeLatitude = GetlongitudeLatitude(location);
//        WeatherRequset.LocationName(LongitudeLatitude,handler,GETLOCATION_SUCCESS,GETLOCATION_FALL);
    }

    /*获取权限*/
    private void CheckPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                ) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionList.isEmpty()) {
            iniJW();
        } else {
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permission, 1);
        }
    }

//    private String GetlongitudeLatitude(Location mLocation) {
//        if (mLocation != null) {
//            return String.valueOf(mLocation.getLatitude()) +
//                    ":" +
//                    mLocation.getLongitude();
//        }
//        else{
//            Toast.makeText(MainActivity.this, "Location 为null", Toast.LENGTH_SHORT).show();
////            return "39.93:116.40";
//            return "";
//        }
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            finish();
                            break;
                        }
                    }
                    iniJW();
                } else {
                    finish();
                }
                break;
            default:
        }
    }


    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Message message = new Message();
            message.what = GETLOCATION_SUCCESS;
            message.obj = bdLocation.getDistrict().substring(0, bdLocation.getDistrict().length() - 1);
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("生命周期", "onDestroy");
        locationClient.stop();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e("生命周期", "onSaveInstanceState");
    }
}
