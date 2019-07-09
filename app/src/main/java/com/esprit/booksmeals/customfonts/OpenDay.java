package com.esprit.booksmeals.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by praja on 17-May-17.
 */

public class OpenDay extends TextView {

    public OpenDay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OpenDay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenDay(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ONEDAY.ttf");
            setTypeface(tf);
        }
    }
}
