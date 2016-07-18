package com.android.jikexueyuan.drawableall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by admin on 2015/12/14.
 */
public class DrawableGeneral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawablegeneral);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.example);

        ( (ImageView)findViewById(R.id.pic1)).setImageDrawable(new RoundRectImageDrawable(bitmap));
        ( (ImageView)findViewById(R.id.pic2)).setImageDrawable(new CircleImageDrawable(bitmap));



    }



}
