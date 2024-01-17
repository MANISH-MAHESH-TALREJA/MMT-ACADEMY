package com.pixelnx.eacademy.ui.noticeAnnouncement;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pixelnx.eacademy.ui.noticeAnnouncement.fragments.FragmentsAll;
import com.pixelnx.eacademy.ui.noticeAnnouncement.fragments.FragmentsPersonal;


public class PagerAdopter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FragmentsPersonal tab1;
    FragmentsAll tab2;

    public PagerAdopter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab1 = new FragmentsPersonal();
                return tab1;
            case 1:
                tab2 = new FragmentsAll();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}