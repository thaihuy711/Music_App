package com.framgia.music_16.data.source.remote;

import android.util.Log;

import com.framgia.music_16.data.model.Artist;
import com.framgia.music_16.data.model.MoreData;
import com.framgia.music_16.data.model.Song;
import com.framgia.music_16.data.source.OnFetchDataListener;
import com.framgia.music_16.data.source.RequestDataCallBack;
import com.framgia.music_16.data.source.SongDataSource;
import com.framgia.music_16.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongRemoteDataSource implements SongDataSource.RemoteDataSource {

    private static SongRemoteDataSource sSongRemoteDataSource;
    private static final String TAG = "Error";

    public static synchronized SongRemoteDataSource getInstance() {
        if (sSongRemoteDataSource == null) {
            sSongRemoteDataSource = new SongRemoteDataSource();
        }
        return sSongRemoteDataSource;
    }

    private MoreData parseJSON(String jsonString) throws JSONException {
        MoreData moreData = new MoreData();
        List<Song> songList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.COLLECTION);
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject songJsonObject = jsonArray.getJSONObject(i);
                Song song = new Song();
                song.setId(songJsonObject.getInt(Song.SongEntry.ID));
                song.setArtworkUrl(songJsonObject.getString(Song.SongEntry.ARTWORK_URL));
                song.setGenre(songJsonObject.getString(Song.SongEntry.GENRE));
                song.setStreamUrl(songJsonObject.getString(Song.SongEntry.STREAM_URL));
                song.setUri(songJsonObject.getString(Song.SongEntry.URI));
                song.setTitle(songJsonObject.getString(Song.SongEntry.TITLE));

                JSONObject artistJsonObject = songJsonObject.getJSONObject(Constant.USER);
                Artist artist = new Artist();
                artist.setId(artistJsonObject.getInt(Artist.ArtistEntry.USER_ID));
                artist.setAvatarUrl(artistJsonObject.getString(Artist.ArtistEntry.AVATAR_URL));
                artist.setUsername(artistJsonObject.getString(Artist.ArtistEntry.USER_NAME));
                song.setArtist(artist);
                songList.add(song);
            }
            String nextHref = jsonObject.getString(Constant.NEXT_HREF);
            moreData.setSongList(songList);
            moreData.setNextHref(nextHref);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return moreData;
    }

    @Override
    public void getSongsByGenre(String genre, final RequestDataCallBack<MoreData> callBack) {
        new FetchDataFromUrl(new OnFetchDataListener() {
            @Override
            public void onSuccess(String data) {
                MoreData moreData = null;
                try {
                    moreData = parseJSON(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess(moreData);
            }

            @Override
            public void onFail(Exception e) {
                callBack.onFail(e);
            }
        }).execute(Constant.GENRES_URL + genre);
    }
}
