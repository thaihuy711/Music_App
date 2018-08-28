package com.framgia.music_16.screen;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
