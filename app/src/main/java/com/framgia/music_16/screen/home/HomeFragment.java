package com.framgia.music_16.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.framgia.music_16.R;
import com.framgia.music_16.screen.BaseFragment;
import com.framgia.music_16.screen.songgenres.SongGenresFragment;
import com.framgia.music_16.utils.Constant;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ImageView imageViewAlternativeRock = view.findViewById(R.id.image_song_alternativerock);
        ImageView imageViewAmbient = view.findViewById(R.id.image_song_ambient);
        ImageView imageViewClassical = view.findViewById(R.id.image_song_classical);
        ImageView imageViewCountry = view.findViewById(R.id.image_song_country);

        imageViewAlternativeRock.setOnClickListener(this);
        imageViewAmbient.setOnClickListener(this);
        imageViewCountry.setOnClickListener(this);
        imageViewClassical.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.image_song_alternativerock:
                fragment = SongGenresFragment.getGenreFragment(Constant.Genres.ALTERNATIVEROCK);
                break;
            case R.id.image_song_country:
                fragment = SongGenresFragment.getGenreFragment(Constant.Genres.COUNTRY);
                break;
            case R.id.image_song_ambient:
                fragment = SongGenresFragment.getGenreFragment(Constant.Genres.AMBIENT);
                break;
            case R.id.image_song_classical:
                fragment = SongGenresFragment.getGenreFragment(Constant.Genres.CLASSICAL);
                break;
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frame_content_home, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
