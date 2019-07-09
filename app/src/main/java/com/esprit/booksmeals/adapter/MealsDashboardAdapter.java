package com.esprit.booksmeals.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.esprit.booksmeals.model.Meals;
import booksmeals.R;

import java.util.ArrayList;

public class MealsDashboardAdapter  extends BaseAdapter {

    Context context;
    ArrayList<Meals> mealsList;

    public MealsDashboardAdapter(Context context, ArrayList<Meals> beans) {
        this.mealsList = beans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mealsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mealsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.gridview, null);


            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);


            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        Meals meals = (Meals) getItem(position);

        viewHolder.image.setImageResource(meals.getImage());

        return convertView;
    }

    private class ViewHolder {
        ImageView image;

    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;


    }
}