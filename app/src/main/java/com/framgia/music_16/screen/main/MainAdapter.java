package com.framgia.music_16.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.music_16.screen.home.HomeFragment;
import com.framgia.music_16.screen.mysong.MySongFragment;
import com.framgia.music_16.screen.search.SearchFragment;
import com.framgia.music_16.utils.Constant;

public class MainAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 3;

    MainAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constant.Tab.TAB_HOME:
                return new HomeFragment();
            case Constant.Tab.TAB_MY_SONG:
                return new MySongFragment();
            case Constant.Tab.TAB_SEARCH:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
