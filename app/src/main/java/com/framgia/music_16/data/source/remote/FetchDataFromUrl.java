package com.framgia.music_16.data.source.remote;

import android.os.AsyncTask;

import com.framgia.music_16.data.source.OnFetchDataListener;
import com.framgia.music_16.utils.Constant;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDataFromUrl extends AsyncTask<String, Void, String> {

    private OnFetchDataListener mOnFetchDataListener;

    public FetchDataFromUrl(OnFetchDataListener onFetchDataListener) {
        mOnFetchDataListener = onFetchDataListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            data = getJSONFromURL(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mOnFetchDataListener.onSuccess(s);
    }

    private String getJSONFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(urlString);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constant.REQUEST_METHOD);
        httpURLConnection.setReadTimeout(Constant.READ_TIMEOUT);
        httpURLConnection.setConnectTimeout(Constant.CONNECT_TIMEOUT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }
}
