package com.denyszaiats.magicsquare.drawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawRect extends DrawView {

    public DrawRect(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        int xPos = (canvas.getWidth() / 3);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint2.descent() + paint2.ascent()) / 2)) ;
        paint2.setColor(Color.BLACK);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setAntiAlias(true);
        paint2.setTextSize(xPos);
        paint2.setFakeBoldText(true);
        canvas.drawText(text, xPos, yPos, paint2);
        canvas.drawRect(startX, startY, startX + sideSize, startY + sideSize, paint);
    }

}
