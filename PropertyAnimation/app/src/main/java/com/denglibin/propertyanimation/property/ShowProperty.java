package com.denglibin.propertyanimation.property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.denglibin.propertyanimation.R;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
public class ShowProperty extends AppCompatActivity implements View.OnClickListener {
    private ImageView example, ball;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showproperty);

        example = (ImageView) findViewById(R.id.example);

        ball = (ImageView) findViewById(R.id.ball);
        findViewById(R.id.p1).setOnClickListener(this);
        findViewById(R.id.p2).setOnClickListener(this);
        findViewById(R.id.p3).setOnClickListener(this);
        findViewById(R.id.p4).setOnClickListener(this);
        findViewById(R.id.p5).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

            ObjectAnimator anim;
            switch (v.getId()) {
                case R.id.p1:
                    //TODO implement
                    example.setPivotX(50);
                    example.setPivotY(50);

                    ObjectAnimator.ofFloat(example, "rotationY", 0.0F, 360.0F)//
                            .setDuration(3000).start();
                    ObjectAnimator.ofFloat(example, "rotationX", 0.0F, 360.0F)//
                            .setDuration(3000).start();
                    break;
                case R.id.p2:
                    PropertyValuesHolder x = PropertyValuesHolder.ofFloat("x", 0, 250);
                    PropertyValuesHolder y = PropertyValuesHolder.ofFloat("y", 0, 250);
                    PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
                    PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
                    PropertyValuesHolder ry = PropertyValuesHolder.ofFloat("rotationY", 0, 360f);
                    ObjectAnimator.ofPropertyValuesHolder(example, x, y, sx, sy, ry).setDuration(3000).start();

                    break;
                case R.id.p3:
                    //TODO implement

                    Keyframe k1 = Keyframe.ofFloat(0, 0);
                    k1.setInterpolator(new LinearInterpolator());
                    Keyframe k2 = Keyframe.ofFloat(0.5f, 400);
                    k2.setInterpolator(new BounceInterpolator());
                    Keyframe k3 = Keyframe.ofFloat(1, 800);
                    k3.setInterpolator(new BounceInterpolator());
                    PropertyValuesHolder ty = PropertyValuesHolder.ofKeyframe("y", k1, k2, k3);
                    ObjectAnimator.ofPropertyValuesHolder(ball, ty).setDuration(3000).start();

                    break;
                case R.id.p4:
                    Animator anim0 = AnimatorInflater.loadAnimator(ShowProperty.this, R.animator.animatorset);
                    anim0.setTarget(example);
                    anim0.start();
                    break;
                case R.id.p5:
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 500);
                    valueAnimator.setDuration(3000);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.setTarget(ball);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ball.setX(value);
                            ball.setY(0.008f * value * value);
                        }
                    });
                    valueAnimator.start();
                    break;
            }

    }
}
