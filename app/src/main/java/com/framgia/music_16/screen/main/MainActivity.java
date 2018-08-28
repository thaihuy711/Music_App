package com.framgia.music_16.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import com.framgia.music_16.R;
import com.framgia.music_16.screen.BaseActivity;
import com.framgia.music_16.screen.playmusic.PlayMusicFragment;
import com.framgia.music_16.utils.Constant;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView mBottomNavigationView;
    private UnSwipeViewpager mViewPager;
    private ConstraintLayout mConstraintLayout;
    private FragmentManager mFragmentManager;
    private PlayMusicFragment mPlayMusicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getExtras() != null) {
            String a = getIntent().getStringExtra("PlayMusic");
            if (a.equals("PlayMusic")) {
                showFragment();
            }
        }
        initView();
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.navigation_bottom);
        mViewPager = findViewById(R.id.view_pager);
        mConstraintLayout = findViewById(R.id.constraint_item_play_song);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mConstraintLayout.setOnClickListener(this);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.setPagingEnabled(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mViewPager.setCurrentItem(Constant.Tab.TAB_HOME);
                break;
            case R.id.action_my_song:
                mViewPager.setCurrentItem(Constant.Tab.TAB_MY_SONG);
                break;
            case R.id.action_search:
                mViewPager.setCurrentItem(Constant.Tab.TAB_SEARCH);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.constraint_item_play_song:
                showPlayMusicFragment();
                mBottomNavigationView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mFragmentManager = getSupportFragmentManager();
        mPlayMusicFragment = (PlayMusicFragment) mFragmentManager.findFragmentByTag(
                PlayMusicFragment.class.getSimpleName());
        if (mPlayMusicFragment != null && !mPlayMusicFragment.isHidden()) {
            hideFragmentPlayMusicToBottomLayout();
            return;
        } else {
            checkFragmentBackPressed();
            return;
        }
    }

    public void hideFragmentPlayMusicToBottomLayout() {
        mConstraintLayout.setVisibility(View.VISIBLE);
        mBottomNavigationView.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_out_up);
        fragmentTransaction.hide(mPlayMusicFragment);
        fragmentTransaction.commit();
    }

    public void checkFragmentBackPressed() {
        mFragmentManager = getSupportFragmentManager();
        for (Fragment frag : mFragmentManager.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
    }

    public void setNavigationVisibility(boolean isChecked) {
        if (isChecked) {
            mBottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            mBottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void showPlayMusicFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        mPlayMusicFragment = (PlayMusicFragment) mFragmentManager.findFragmentByTag(
                PlayMusicFragment.class.getSimpleName());
        if (mPlayMusicFragment != null && mPlayMusicFragment.isHidden()) {
            fragmentTransaction.show(mPlayMusicFragment);
            fragmentTransaction.commit();
        }
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        PlayMusicFragment playMusicFragment = (PlayMusicFragment) fragmentManager.findFragmentByTag(
                PlayMusicFragment.class.getSimpleName());
        if (playMusicFragment != null && playMusicFragment.isHidden()) {
            fragmentTransaction.show(playMusicFragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(R.id.content_main, new PlayMusicFragment(),
                    PlayMusicFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
