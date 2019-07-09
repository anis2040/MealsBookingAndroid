package com.esprit.booksmeals.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esprit.booksmeals.model.Favoris;

import java.util.ArrayList;

public class DB_Controller extends SQLiteOpenHelper {
    public DB_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Restaurant.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE FAVORIS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT  ,ADDRESS TEXT,PRICEMIN FLOAT, PRICEMAX FLOAT, DATE TEXT, PROFILE_PHOTO TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FAVORIS;");
        onCreate(sqLiteDatabase);
    }

    public void clearDatabase() {
        this.getWritableDatabase().execSQL("DROP TABLE FAVORIS;");
        this.getWritableDatabase().execSQL("CREATE TABLE FAVORIS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT  ,ADDRESS TEXT,PRICEMIN FLOAT, PRICEMAX FLOAT, DATE TEXT ,PROFILE_PHOTO TEXT );");

    }

    public void insert_favoris(String name , String address , float priceMin , float priceMax, String date, String PROFILE_PHOTO  )
    {
        ContentValues content = new ContentValues();
        content.put("NAME", name);
        content.put("ADDRESS",address);
        content.put("PRICEMIN",priceMin);
        content.put("PRICEMAX",priceMax);
        content.put("DATE",date);
        content.put("DATE",date);
        content.put("PROFILE_PHOTO",PROFILE_PHOTO);
        this.getWritableDatabase().insertOrThrow("FAVORIS", "", content);

    }



    public void delete_favoris(String firstname){
        SQLiteDatabase db = this.getWritableDatabase();
        this.getWritableDatabase().delete("FAVORIS","NAME='"+firstname+"'",null);
        display_favourites();
        db.close();

    }

    public ArrayList<Favoris> display_favourites()
    {
        ArrayList<Favoris> favories =new ArrayList<>() ;
        String select_query="select * from FAVORIS";
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query,null);

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String minPrice = cursor.getString(5);
                String maxPrice = cursor.getString(4);
                String date = cursor.getString(3);
                String PROFILE_PHOTO = cursor.getString(6);
                Favoris c =new Favoris(name,address,minPrice,maxPrice,date, PROFILE_PHOTO);
                favories.add(c);
            }while(cursor.moveToNext());
        }


        return favories;

    }

    public int getFavorisCount() {
        String countQuery = "SELECT  * FROM FAVORIS" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


}
