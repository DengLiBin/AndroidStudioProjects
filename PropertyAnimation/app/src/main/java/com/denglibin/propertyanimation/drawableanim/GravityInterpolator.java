package com.denglibin.propertyanimation.drawableanim;


import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


/**
 * Created by admin on 2015/11/22.
 */
public class GravityInterpolator implements Interpolator {
//extends BaseInterpolator implements NativeInterpolatorFactory

        @Override
    public float getInterpolation(float input) {
            float f=0;
            if(input<0.5f)
            {
                f=4*input-1f;
                return  1f  -f*f;
            }else{
                f=input-0.5f;
                f=(4*f-1f);
                return ( 1f  -f*f)*0.4f;
            }



    }
}
