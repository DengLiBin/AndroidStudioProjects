package com.denglibin.propertyanimation.drawableanim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.denglibin.propertyanimation.R;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
public class TestInterpolator extends AppCompatActivity implements View.OnClickListener {
    private ImageView ball;
    private ImageView ball2;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testinterpolator);

        ball = (ImageView) findViewById(R.id.ball);
        ball2 = (ImageView) findViewById(R.id.ball2);
        findViewById(R.id.interp1).setOnClickListener(this);
        findViewById(R.id.interp2).setOnClickListener(this);
        findViewById(R.id.interp3).setOnClickListener(this);
        findViewById(R.id.interp4).setOnClickListener(this);
        findViewById(R.id.interp5).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.interp1:
                //TODO implement
                animation = AnimationUtils.loadAnimation(this, R.anim.translate2);
                animation.setInterpolator(new AccelerateInterpolator());

                break;
            case R.id.interp2:
                //TODO implement
                animation = new TranslateAnimation(0, 300, 0, 0);

                animation.setInterpolator(new BounceInterpolator());

                break;
            case R.id.interp3:
                //TODO implement
                animation = new TranslateAnimation(0, 300, 0, 0);
                animation.setInterpolator(new AnticipateOvershootInterpolator());

                break;
            case R.id.interp4:
                //TODO implement
                animation = AnimationUtils.loadAnimation(this, R.anim.translate2);

                break;
            case R.id.interp5:
                //TODO implement
                animation = new TranslateAnimation(0, 0, 0, -300);



                animation.setInterpolator(new GravityInterpolator());

                break;
        }
        ball.setVisibility(View.VISIBLE);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        ball.startAnimation(animation);
    }
}
