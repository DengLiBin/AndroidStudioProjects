package com.android.jikexueyuan.drawableall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;

/**
 * Created by admin on 2015/12/16.
 */
public class ThirdAc extends AppCompatActivity {
    private ImageView img;
    private RadioGroup radioGroup;
    private ArcShape arcShape;
    private ShapeDrawable shapeDrawable, shapeDrawable2, shapeDrawable3, shapeDrawable4, shapeDrawable5;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        img = (ImageView) findViewById(R.id.img);

        radioGroup = (RadioGroup) findViewById(R.id.rg);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.example);
        arcShape = new ArcShape(45, 270);
        shapeDrawable = new ShapeDrawable(arcShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(Color.RED);

        img.setBackground(shapeDrawable);

        arcShape = new ArcShape(45, 270);
        shapeDrawable2 = new ShapeDrawable(arcShape);

        arcShape = new ArcShape(45, 270);
        shapeDrawable3 = new ShapeDrawable(arcShape);


        arcShape = new ArcShape(45, 270);
        shapeDrawable4 = new ShapeDrawable(arcShape);


        arcShape = new ArcShape(45, 270);
        shapeDrawable5 = new ShapeDrawable(arcShape);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.bitmapshader:
                        img.setBackground(shapeDrawable);
                        break;
                    case R.id.composeshader:
                        img.setBackground(shapeDrawable2);
                        break;
                    case R.id.lineargradient:
                        img.setBackground(shapeDrawable3);
                        break;
                    case R.id.radialgradient:
                        img.setBackground(shapeDrawable4);
                        break;
                    case R.id.sweepgradient:
                        img.setBackground(shapeDrawable5);
                        break;


                }


            }
        });
        shapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
            }
        });
        shapeDrawable2.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient mLinearGradient = new LinearGradient(0, 0, width, height,
                        new int[]{
                                Color.RED,
                                Color.GREEN,
                                Color.BLUE,
                                Color.YELLOW
                        }, null, Shader.TileMode.REPEAT);
                RadialGradient mRadialGradient = new RadialGradient(width / 2, height / 2, width / 2,
                        new int[]{
                                Color.WHITE,
                                Color.GRAY,
                                Color.DKGRAY,
                                Color.BLACK
                        }, null, Shader.TileMode.REPEAT);
                return new ComposeShader(mLinearGradient, mRadialGradient,
                        PorterDuff.Mode.MULTIPLY);
            }
        });
        shapeDrawable3.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {

                return new LinearGradient(0, 0, width, height,
                        new int[]{
                                Color.RED,
                                Color.GREEN,
                                Color.BLUE,
                                Color.YELLOW
                        }, null, Shader.TileMode.REPEAT);
            }
        });
        shapeDrawable4.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {

                return new RadialGradient(width / 2, height / 2, width / 2,
                        new int[]{
                                Color.RED,
                                Color.GREEN,
                                Color.BLUE,
                                Color.YELLOW
                        }, null, Shader.TileMode.REPEAT);
            }
        });

        shapeDrawable5.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {

                return new SweepGradient(width / 2, height / 2,
                        new int[]{
                                Color.RED,
                                Color.GREEN,
                                Color.BLUE,
                                Color.YELLOW
                        }, new float[]{0.1f, 0.3f, 0.6f, 1f});
            }
        });
    }
}
