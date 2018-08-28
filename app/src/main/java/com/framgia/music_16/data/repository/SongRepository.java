package com.framgia.music_16.data.repository;

import com.framgia.music_16.data.model.MoreData;
import com.framgia.music_16.data.source.RequestDataCallBack;
import com.framgia.music_16.data.source.SongDataSource;
import com.framgia.music_16.data.source.remote.SongRemoteDataSource;

public final class SongRepository implements SongDataSource.RemoteDataSource {

    private static SongRepository sInstance;
    private SongRemoteDataSource mSongRemoteDataSource;

    private SongRepository(SongRemoteDataSource songRemoteDataSource) {
        mSongRemoteDataSource = songRemoteDataSource;
    }

    public static synchronized SongRepository getInstance(SongRemoteDataSource songRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(songRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void getSongsByGenre(String genre, RequestDataCallBack<MoreData> callBack) {
        mSongRemoteDataSource.getSongsByGenre(genre, callBack);
    }
}
