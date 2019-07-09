package com.esprit.booksmeals.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by praja on 17-May-17.
 */

public class MavenPro_regular extends Button {

    public MavenPro_regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MavenPro_regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MavenPro_regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
            setTypeface(tf);
        }
    }
}
