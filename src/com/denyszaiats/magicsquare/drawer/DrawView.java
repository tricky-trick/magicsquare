package com.denyszaiats.magicsquare.drawer;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public abstract class DrawView extends View {

    public int backgroundColor;
    public int radius;
    public int sideSize;
    public int startX, startY;
    public Point startPoint;
    public Point endPoint;
    public String text;

    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    public void setSideSize(int sideSize) {
        this.sideSize = sideSize;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setText(String text) { this.text = text; }

    public String getText() { return this.text; }
}