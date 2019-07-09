package com.esprit.booksmeals.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.esprit.booksmeals.fragment.BannerFragment;
import com.esprit.booksmeals.fragment.Pub1Fragment;

import java.util.Timer;

/**
 * Created by wolfsoft on 10/11/2015.
 */
public class PagerAdapterForBanner extends FragmentStatePagerAdapter {

    Timer timer;
    int page = 1;


    public PagerAdapterForBanner(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Pub1Fragment tab1 = new Pub1Fragment();
                return tab1;

            case 1:
                Pub1Fragment tab6 = new Pub1Fragment();
                return tab6;


            case 2:
                BannerFragment tab2 = new BannerFragment();
                return tab2;


            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}
