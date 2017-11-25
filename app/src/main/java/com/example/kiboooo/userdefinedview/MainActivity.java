package com.example.kiboooo.userdefinedview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.kiboooo.userdefinedview.View.ImageBarnnerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageBarnnerFrameLayout.ImageBarnnerListener {

//    private ImageBarnnerViewGroup mGroup;
    private ImageBarnnerFrameLayout mGroup;

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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bean.WITTH = dm.widthPixels;
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
}
