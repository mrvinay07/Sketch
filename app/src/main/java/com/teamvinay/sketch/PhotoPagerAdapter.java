package com.teamvinay.sketch;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class PhotoPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList = new ArrayList();
    public List<String> fragmentTitles = new ArrayList();

    @SuppressLint({"WrongConstant"})
    public PhotoPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, 1);
    }

    public void addFragment(Fragment fragment, String str) {
        this.fragmentList.add(fragment);
        this.fragmentTitles.add(str);
    }

    public String getPageTitle(int i) {
        return this.fragmentTitles.get(i);
    }

    public Fragment getItem(int i) {
        return this.fragmentList.get(i);
    }

    public int getCount() {
        return this.fragmentList.size();
    }
}
