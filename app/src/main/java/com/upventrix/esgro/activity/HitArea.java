package com.upventrix.esgro.activity;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;

public class HitArea {

    public void setHitArea(ImageView imageView,int size){
        final View parent = (View) imageView.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                imageView.getHitRect(rect);
                rect.top -= size;    // increase top hit area
                rect.left -= size;   // increase left hit area
                rect.bottom += size; // increase bottom hit area
                rect.right += size;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , imageView));
            }
        });
    }
}
