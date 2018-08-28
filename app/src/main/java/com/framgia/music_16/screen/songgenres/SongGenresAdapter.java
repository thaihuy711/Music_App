package com.framgia.music_16.screen.songgenres;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_16.R;
import com.framgia.music_16.data.model.Song;
import com.framgia.music_16.screen.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SongGenresAdapter extends BaseRecyclerViewAdapter<SongGenresAdapter.ViewHolder> {

    private List<Song> mSongs = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    public SongGenresAdapter(Context mContext, ItemClickListener itemClickListener) {
        super(mContext);
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_my_song, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mSongs.get(position));
    }

    public void addData(List<Song> songs) {
        if (songs == null) {
            return;
        }
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageViewGenre;
        private TextView mTextViewGenre;
        private TextView mTextViewArtist;
        private ItemClickListener mClickListener;

        public ViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            mImageViewGenre = itemView.findViewById(R.id.image_artist_my_song);
            mTextViewGenre = itemView.findViewById(R.id.text_song_name);
            mTextViewArtist = itemView.findViewById(R.id.text_artist);
            mClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClicked(getAdapterPosition());
        }

        void setData(Song song) {
            mTextViewGenre.setText(song.getTitle());
            mTextViewArtist.setText(song.getArtist().getUsername());
            loadImage(song);
        }

        void loadImage(Song song) {
            Glide.with(itemView.getContext())
                    .load(song.getArtworkUrl())
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.image_country))
                    .into(mImageViewGenre);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public interface ItemClickListener {
        void onItemClicked(int position);
    }
}
