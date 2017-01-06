package com.goka.bordertextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import static com.goka.bordertextview.BorderTextView.Border.Bottom;
import static com.goka.bordertextview.BorderTextView.Border.Left;
import static com.goka.bordertextview.BorderTextView.Border.Right;
import static com.goka.bordertextview.BorderTextView.Border.Top;

public class BorderTextView extends TextView {

    public int borderWidth;
    public int borderColor;

    public enum Border {
        Left, Top, Right, Bottom
    }

    public Border[] borders = new Border[Border.values().length];

    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView, defStyleAttr, 0);

        borderWidth = a.getInt(R.styleable.BorderTextView_borderWidth, 10);
        borderColor = a.getColor(R.styleable.BorderTextView_borderColor, Color.WHITE);

        if (a.getBoolean(R.styleable.BorderTextView_borderLeft, false)) {
            borders[Left.ordinal()] = Left;
        }
        if (a.getBoolean(R.styleable.BorderTextView_borderTop, false)) {
            borders[Top.ordinal()] = Top;
        }
        if (a.getBoolean(R.styleable.BorderTextView_borderRight, false)) {
            borders[Right.ordinal()] = Right;
        }
        if (a.getBoolean(R.styleable.BorderTextView_borderBottom, false)) {
            borders[Bottom.ordinal()] = Bottom;
        }

        drawBorder();

        a.recycle();
    }

    private void drawBorder() {
        GradientDrawable borderDrawable = new GradientDrawable();
        borderDrawable.setStroke(borderWidth, borderColor);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{borderDrawable});
        setupLayer(layerDrawable);
        setBackground(layerDrawable);
    }

    private void setupLayer(LayerDrawable layerDrawable) {
        int left = -borderWidth, top = -borderWidth, right = -borderWidth, bottom = -borderWidth;

        for (Border b: borders) {
            if (b == null) {
                continue;
            }
            switch (b) {
                case Left:
                    left = 0;
                    break;
                case Top:
                    top = 0;
                    break;
                case Right:
                    right = 0;
                    break;
                case Bottom:
                    bottom = 0;
                    break;
            }
        }

        layerDrawable.setLayerInset(0, left, top, right, bottom);
    }

}
