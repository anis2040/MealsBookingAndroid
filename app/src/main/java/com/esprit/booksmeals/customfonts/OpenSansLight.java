package com.esprit.booksmeals.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OpenSansLight extends TextView {

    public OpenSansLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OpenSansLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Light.ttf");
            setTypeface(tf);
        }
    }
}