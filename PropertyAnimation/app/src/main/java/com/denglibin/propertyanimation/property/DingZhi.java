package com.denglibin.propertyanimation.property;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.denglibin.propertyanimation.R;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
public class DingZhi extends AppCompatActivity implements View.OnClickListener {
    private ImageView example, ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showproperty);

        example = (ImageView) findViewById(R.id.example);

        ball = (ImageView) findViewById(R.id.ball);
        findViewById(R.id.p1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        {
            ObjectAnimator anim;
            switch (v.getId()) {
                case R.id.p1:
                    //TODO implement
                    ObjectAnimator.ofFloat(ball, "x", 0, 500)//
                            .setDuration(3000).start();
                    anim = ObjectAnimator.ofFloat(ball, "y", 0, 500)//
                            .setDuration(3000);
                    anim.setEvaluator(new TypeEvaluator<Float>() {
                        @Override
                        public Float evaluate(float fraction, Float startValue, Float endValue) {
                            return startValue + 0.008f * fraction * (endValue - startValue) * fraction * (endValue - startValue);
                        }

                    });
                    anim.start();
                    break;

            }
        }
    }
}
