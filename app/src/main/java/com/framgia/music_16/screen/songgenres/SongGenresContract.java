package com.framgia.music_16.screen.songgenres;

import com.framgia.music_16.data.model.Song;
import com.framgia.music_16.screen.BasePresenter;

import java.util.List;

public interface SongGenresContract {
    interface View {
        void showGenres(List<Song> songs);

        void onError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongsByGenre(String genre);
    }
}
