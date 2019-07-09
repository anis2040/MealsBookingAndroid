package com.esprit.booksmeals.adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.esprit.booksmeals.fragment.RestaurantPager2Fragment;
import com.esprit.booksmeals.fragment.RestaurantPager3Fragment;
import com.esprit.booksmeals.fragment.RestaurantPagerFragment;


/**
 * Created by wolfsoft on 10/11/2015.
 */
public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {

    private int id;
    public RestaurantPagerAdapter(FragmentManager fm,int id) {
        super(fm);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RestaurantPagerFragment tab1 = new RestaurantPagerFragment(id);
                return tab1;

            case 1:
                RestaurantPager2Fragment tab6 = new RestaurantPager2Fragment(id);
                return tab6;

            case 2:
                RestaurantPager3Fragment tab2 = new RestaurantPager3Fragment(id);
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}