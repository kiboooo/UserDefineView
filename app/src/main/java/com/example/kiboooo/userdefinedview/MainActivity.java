package com.example.kiboooo.userdefinedview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.kiboooo.userdefinedview.View.ImageBarnnerViewGroup;

public class MainActivity extends AppCompatActivity {

    private ImageBarnnerViewGroup mGroup;

    private int[] ids = new int[]{
            R.drawable.first_hanshake,
            R.drawable.second_hanshake,
            R.drawable.thirt_hanshake
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroup = (ImageBarnnerViewGroup) findViewById(R.id.image_group);

        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(ids[i]);
            mGroup.addView(imageView);
        }
    }
}
