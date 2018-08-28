package com.framgia.music_16.data.source;

public interface RequestDataCallBack<T> {
    void onSuccess(T data);

    void onFail(Exception e);
}
