package com.framgia.music_16.data.source;

import com.framgia.music_16.data.model.MoreData;

public interface SongDataSource {

    interface RemoteDataSource extends SongDataSource {
        void getSongsByGenre(String genre, RequestDataCallBack<MoreData> callBack);
    }
}
