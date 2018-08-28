package com.framgia.music_16.screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;

    protected BaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }
}
