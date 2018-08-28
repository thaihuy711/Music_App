package com.framgia.music_16.screen.songgenres;

import com.framgia.music_16.data.model.MoreData;
import com.framgia.music_16.data.repository.SongRepository;
import com.framgia.music_16.data.source.RequestDataCallBack;

public class SongGenresPresenter implements SongGenresContract.Presenter {

    private SongGenresContract.View mView;
    private SongRepository mSongRepository;

    public SongGenresPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void setView(SongGenresContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getSongsByGenre(String genre) {
        mSongRepository.getSongsByGenre(genre, new RequestDataCallBack<MoreData>() {
            @Override
            public void onSuccess(MoreData data) {
                mView.showGenres(data.getSongList());
            }

            @Override
            public void onFail(Exception e) {
                mView.onError(e);
            }
        });
    }
}
