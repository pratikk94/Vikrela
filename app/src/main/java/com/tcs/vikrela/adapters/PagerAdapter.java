package com.tcs.vikrela.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tcs.vikrela.fragments.HawkerFragment;
import com.tcs.vikrela.fragments.PendingFragment;

/**
 * Created by Harsh on 4/7/2017.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HawkerFragment();
                break;
            case 1:
                fragment = new PendingFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Hawker Info";
            case 1:
                return "Pending";
            default:
                return null;
        }

    }
}
