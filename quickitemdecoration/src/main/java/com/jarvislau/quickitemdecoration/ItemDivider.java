package com.jarvislau.quickitemdecoration;

import android.graphics.BitmapFactory;
import android.graphics.Paint;

/**
 * Created by JarvisLau on 2018/7/13.
 * Description :
 */

public class ItemDivider {

    private Paint paint;
    private int marginLeft;
    private int marginTop;
    private int marginRight;
    private int marginBottom;
    private int color;
    private int itemGridBackgroundColor;
    private int width;
    //private int corners;

    public ItemDivider() {
        paint = new Paint();
        paint.setAntiAlias(true);
        color = 0xFF666666;
        paint.setColor(color);
        marginLeft = 0;
        marginTop = 0;
        marginRight = 0;
        marginBottom = 0;
        width = 1;
        itemGridBackgroundColor = 0xFFFFFFFF;
        //corners = 0;
    }

    public Paint getPaint() {
        return paint;
    }

    public ItemDivider setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public ItemDivider setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public ItemDivider setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public ItemDivider setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public ItemDivider setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    public int getColor() {
        return color;
    }

    public ItemDivider setColor(int color) {
        this.color = color;
        paint.setColor(color);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ItemDivider setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getItemGridBackgroundColor() {
        return itemGridBackgroundColor;
    }

    public ItemDivider setItemGridBackgroundColor(int itemGridBackgroundColor) {
        this.itemGridBackgroundColor = itemGridBackgroundColor;
        return this;
    }

    /*public int getCorners() {
        return corners;
    }

    public ItemDivider setCorners(int corners) {
        this.corners = corners;
        return this;
    }*/

}