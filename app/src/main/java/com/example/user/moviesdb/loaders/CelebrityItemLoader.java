package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.CelebrityItemList;
import com.example.user.moviesdb.data.MovieItemList;

import java.util.List;

public class CelebrityItemLoader extends AsyncTaskLoader<List<CelebrityItemList>> {
    private static final String LOG_TAG = MovieItemLoader.class.getName();
    private String mUrl ;

    public CelebrityItemLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<CelebrityItemList> loadInBackground() {
        if (this.mUrl == null){
            return null;
        }
        Log.d(LOG_TAG, this.mUrl+"");
        List<CelebrityItemList> celebrity_list = QueryUtils.fetchCelebrityListData(this.mUrl);
        return celebrity_list;
    }
}
