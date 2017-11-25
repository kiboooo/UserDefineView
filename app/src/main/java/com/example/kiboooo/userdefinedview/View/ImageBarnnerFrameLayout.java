package com.example.kiboooo.userdefinedview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kiboooo.userdefinedview.Bean;
import com.example.kiboooo.userdefinedview.R;

import java.util.List;

/**
 * Created by Kiboooo on 2017/11/25.
 */

public class ImageBarnnerFrameLayout extends FrameLayout implements ImageBarnnerViewGroup.ImageBarnnerViewGroupLinsnner,ImageBarnnerViewGroup.ImageBarnnerListener{

    private ImageBarnnerViewGroup imageBarnnerViewGroup;
    private LinearLayout linearLayout;



    private ImageBarnnerListener listeners;
    public ImageBarnnerListener getListener() {
        return listeners;
    }

    public void setListener(ImageBarnnerListener listener) {
        this.listeners = listener;
    }


    public ImageBarnnerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    private void initImageBarnnerViewGroup(){
        imageBarnnerViewGroup = new ImageBarnnerViewGroup(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.
                LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        imageBarnnerViewGroup.setLayoutParams(lp);
        imageBarnnerViewGroup.setImageBarnnerViewGroupLinsnner(this);
        imageBarnnerViewGroup.setListener(this);

        addView(imageBarnnerViewGroup);
    }

    private void initDotLinearLayout(){
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        lp.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.GRAY);
        addView(linearLayout);

//        FrameLayout.LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
//        layoutParams.gravity = Gravity.BOTTOM;
//        linearLayout.setLayoutParams(layoutParams);

        linearLayout.setAlpha(0.5f);//设置透明度

    }

    public void addBitmaps(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            addBitmapToImageBarnnerViewGroup(list.get(i));
            addDotToLinnerLayout();

//            imageBarnnerViewGroup.setListener(imageBarnnerViewGroup.getLayoutParams().getClass());
        }
    }

    private void addBitmapToImageBarnnerViewGroup(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Bean.WITTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        imageBarnnerViewGroup.addView(imageView);
    }

    private void addDotToLinnerLayout(){
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);

    }

    @Override
    public void selectImage(int pos) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (i == pos) {
                imageView.setImageResource(R.drawable.dot_select);

            }else
                imageView.setImageResource(R.drawable.dot_normal);
        }
    }

    @Override
    public void ClickListener(int index) {
        listeners.ClickListener(index);
    }

    public interface ImageBarnnerListener{
        void ClickListener(int index);
    }
}
