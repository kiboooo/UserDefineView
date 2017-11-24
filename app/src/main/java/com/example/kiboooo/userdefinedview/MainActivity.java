package com.example.kiboooo.userdefinedview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kiboooo.userdefinedview.View.ImageBarnnerViewGroup;

public class MainActivity extends AppCompatActivity {

    private ImageBarnnerViewGroup mGroup;

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
        int width = dm.widthPixels;

        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(ids[i]);
            mGroup.addView(imageView);
        }
    }


}
