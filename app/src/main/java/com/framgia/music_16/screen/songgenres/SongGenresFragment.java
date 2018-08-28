package com.framgia.music_16.screen.songgenres;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music_16.R;
import com.framgia.music_16.data.model.Song;
import com.framgia.music_16.data.repository.SongRepository;
import com.framgia.music_16.data.source.remote.SongRemoteDataSource;
import com.framgia.music_16.screen.BaseFragment;
import com.framgia.music_16.screen.playmusic.PlayMusicFragment;
import java.util.List;

public class SongGenresFragment extends BaseFragment
        implements SongGenresContract.View, SongGenresAdapter.ItemClickListener {

    public static String ARGUMENT_GENRE = "ARGUMENT_GENRE";

    private SongGenresPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    private List<Song> mSongs;

    public static SongGenresFragment getGenreFragment(String genre) {
        SongGenresFragment songGenresFragment = new SongGenresFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_GENRE, genre);
        songGenresFragment.setArguments(args);
        return songGenresFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_song, container, false);
        initViews(mView);
        return mView;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_my_song);
        SongRepository songRepository =
                SongRepository.getInstance(SongRemoteDataSource.getInstance());
        String genre = getArguments().getString(ARGUMENT_GENRE);
        mPresenter = new SongGenresPresenter(songRepository);
        mPresenter.setView(this);
        mPresenter.getSongsByGenre(genre);
    }

    @Override
    public void showGenres(List<Song> songs) {
        mSongs = songs;
        SongGenresAdapter adapter = new SongGenresAdapter(getContext(), this);
        adapter.addData(songs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        fragmentTransaction.replace(R.id.content_main,
                PlayMusicFragment.newInstance(mSongs, position),
                PlayMusicFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
