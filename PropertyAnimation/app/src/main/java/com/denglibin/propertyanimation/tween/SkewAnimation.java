package com.denglibin.propertyanimation.tween;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
public class SkewAnimation extends Animation {
    private final float mSkewX;
    private final float mSkewY;


    private Camera mCamera;
    private int type;
    public SkewAnimation(float  mSkewX, float mSkewY )
    {
        this.mSkewX=mSkewX;
        this.mSkewY=mSkewY;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float skewX = mSkewX * interpolatedTime;
        float skewY = mSkewY * interpolatedTime;
        final Matrix matrix = t.getMatrix();
        matrix.preSkew(skewX, skewY);
    }
}
