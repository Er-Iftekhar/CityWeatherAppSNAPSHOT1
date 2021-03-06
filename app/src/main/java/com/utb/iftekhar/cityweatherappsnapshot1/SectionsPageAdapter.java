package com.utb.iftekhar.cityweatherappsnapshot1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syedy on 02-04-2020.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    //These two lists are created to maintain the fragments and their titles.
    List<Fragment> myFragmentList=new ArrayList<>();
    List<String> myFragmentTitleList=new ArrayList<>();

    //Default constructor
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    //Custom method to add fragments and titles to the layout
    public void addFragment(Fragment fragment, String title){
        myFragmentList.add(fragment);
        myFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myFragmentTitleList.get(position);
    }



    @Override
    public Fragment getItem(int position) {
        return myFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return myFragmentList.size();
    }
}
