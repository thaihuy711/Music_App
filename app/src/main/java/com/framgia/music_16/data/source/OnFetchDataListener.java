package com.framgia.music_16.data.source;

public interface OnFetchDataListener {
    void onSuccess(String data);

    void onFail(Exception e);
}
