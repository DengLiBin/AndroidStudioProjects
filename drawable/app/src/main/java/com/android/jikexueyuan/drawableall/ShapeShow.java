package com.android.jikexueyuan.drawableall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2015/12/15.
 */
public class ShapeShow extends AppCompatActivity {
    private RectShape rectShape;
    private OvalShape ovalShape;
    private PathShape pathShape;
    private ArcShape arcShape;
    private ShapeDrawable shapeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shapeshow);




      final  ImageView oval=(ImageView)   findViewById(R.id.oval);
        oval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oval.setImageResource(R.drawable.oval2);
            }
        });
        shapeDrawable=new ShapeDrawable();
        Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.lineTo(50, 0);
        path.close();
        PathShape pathShape = new PathShape(path, 100, 100);

        shapeDrawable = new ShapeDrawable(pathShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(Color.BLUE);
       ((ImageView)findViewById(R.id.path)).setBackground(shapeDrawable);


        arcShape=new ArcShape(180,-270);
        shapeDrawable = new ShapeDrawable(arcShape);
        shapeDrawable.setBounds(0, 0, 100, 150);
        shapeDrawable.getPaint().setStrokeWidth(10);
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setColor(Color.GREEN);

      ((ImageView)findViewById(R.id.arc)).setBackground(shapeDrawable);

        arcShape=new ArcShape(45,270);
        shapeDrawable = new ShapeDrawable(arcShape);
        shapeDrawable.setBounds(0, 0, 200, 200);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(Color.RED);
        ((ImageView)findViewById(R.id.arc2)).setBackground(shapeDrawable);



    }
}
