package com.framgia.music_16.screen.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_16.R;
import com.framgia.music_16.data.model.Song;
import com.framgia.music_16.screen.BaseFragment;
import com.framgia.music_16.screen.main.MainActivity;
import com.framgia.music_16.screen.service.MusicService;
import com.framgia.music_16.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends BaseFragment
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final int DELAY_MILISECONDS = 100;

    private ImageView mImageBack, mImagePrevious, mImagePlay, mImageNext, mImageSong,
            mImageSongParent, mImagePlayParent, mImageNextParent, mImagePreviousParent;
    private TextView mTextSongTittle, mTextSongTittleParent, mTextArtistName, mTextArtistNameParent,
            mTextSongProgress, mTextAllTime;
    private SeekBar mSeekBarProgress;
    private MusicService mMusicService;
    private List<Song> mSongs;
    private int mSongPosition;
    private boolean mIsBound;
    private Utilities mUtilities;
    private Handler mHandler;

    public static PlayMusicFragment newInstance(List<Song> songs, int position) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_SONGS, (ArrayList<? extends Parcelable>) songs);
        args.putInt(Constant.ARGUMENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_song, container, false);
        initViews(view);
        ((MainActivity) getActivity()).setNavigationVisibility(false);
        getData();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_back:
                getActivity().onBackPressed();
                break;
            case R.id.image_button_next_player:
                mMusicService.nextMusic();
                setUpViews();
                break;
            case R.id.image_button_previous_player:
                mMusicService.previousMusic();
                setUpViews();
                break;
            case R.id.image_button_next_item:
                mMusicService.nextMusic();
                setUpViews();
                break;
            case R.id.image_button_previous_item:
                mMusicService.previousMusic();
                setUpViews();
                break;
            case R.id.image_button_play_player:
                checkEventPlayMusic();
                break;
            case R.id.image_button_play_item:
                checkEventPlayMusic();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mMusicService.fastForward(mUtilities.progressToTimer(seekBar.getProgress(),
                    mMusicService.getSongDuration()));
            mTextSongProgress.setText(
                    mUtilities.milliSecondsToTimer(mMusicService.getCurrentPosition()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateProgressBar();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            setUpViews();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mMusicService.getSongDuration();
            long currentDuration = mMusicService.getCurrentPosition();
            String currentPosition = mUtilities.milliSecondsToTimer(currentDuration);
            String totalTime = mUtilities.milliSecondsToTimer(totalDuration);
            if (currentDuration != 0) {
                mTextAllTime.setText(mUtilities.milliSecondsToTimer(totalDuration));
            }
            mTextSongProgress.setText(mUtilities.milliSecondsToTimer(currentDuration));
            int progress = (mUtilities.getProgressPercentage(currentDuration, totalDuration));
            mSeekBarProgress.setProgress(progress);
            if (currentPosition.equals(totalTime) && currentDuration != 0) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                mImagePlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            }
            mHandler.postDelayed(this, DELAY_MILISECONDS);
        }
    };

    public void getData() {
        Bundle args = getArguments();
        if (args != null) {
            mSongs = args.getParcelableArrayList(ARGUMENT_SONGS);
            mSongPosition = args.getInt(Constant.ARGUMENT_POSITION);
            Intent intent = MusicService.getSongsIntent(getActivity(), mSongs, mSongPosition);
            getActivity().startService(intent);
            getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        } else {
            Intent intent = new Intent(getActivity(), MusicService.class);
            getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void initViews(View view) {
        mImageBack = view.findViewById(R.id.image_button_back);
        mImagePrevious = view.findViewById(R.id.image_button_previous_player);
        mImagePlay = view.findViewById(R.id.image_button_play_player);
        mImageNext = view.findViewById(R.id.image_button_next_player);
        mImageSong = view.findViewById(R.id.image_artist_player);
        mImageSongParent = getActivity().findViewById(R.id.image_artist_item);
        mImageNextParent = getActivity().findViewById(R.id.image_button_next_item);
        mImagePreviousParent = getActivity().findViewById(R.id.image_button_previous_item);
        mImagePlayParent = getActivity().findViewById(R.id.image_button_play_item);

        mTextSongTittle = view.findViewById(R.id.text_song_title_player);
        mTextArtistName = view.findViewById(R.id.text_artist_player);
        mTextSongTittleParent = getActivity().findViewById(R.id.text_song_name_item);
        mTextArtistNameParent = getActivity().findViewById(R.id.text_artist_name_item);
        mTextSongProgress = view.findViewById(R.id.text_song_progress);
        mTextAllTime = view.findViewById(R.id.text_all_time);
        mSeekBarProgress = view.findViewById(R.id.seek_progress);

        mImageBack.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageNextParent.setOnClickListener(this);
        mImagePreviousParent.setOnClickListener(this);
        mImagePlayParent.setOnClickListener(this);
        mSeekBarProgress.setOnSeekBarChangeListener(this);

        mHandler = new Handler();
        mUtilities = new Utilities();
    }

    private void setUpViews() {
        mTextSongTittle.setText(mMusicService.getSongName());
        mTextArtistName.setText(mMusicService.getArtistName());
        mTextSongTittleParent.setText(mMusicService.getSongName());
        mTextArtistNameParent.setText(mMusicService.getArtistName());
        loadImageSong();
        loadImageSongParent();
        updateProgressBar();
    }

    private void loadImageSong() {
        Glide.with(getActivity())
                .load(mMusicService.getArtwork())
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.mipmap.ic_launcher_circle))
                .into(mImageSong);
    }

    private void loadImageSongParent() {
        Glide.with(getActivity())
                .load(mMusicService.getArtwork())
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.mipmap.ic_launcher_circle))
                .into(mImageSongParent);
    }

    private void checkEventPlayMusic() {
        if (mMusicService.isPlaying()) {
            mMusicService.pauseMedia();
            mImagePlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            mImagePlayParent.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        } else {
            mMusicService.playMusic();
            mImagePlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            mImagePlayParent.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, DELAY_MILISECONDS);
    }
}
