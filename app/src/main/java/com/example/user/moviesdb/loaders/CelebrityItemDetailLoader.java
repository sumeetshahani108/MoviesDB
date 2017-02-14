package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.CelebrityDetailList;
import com.example.user.moviesdb.data.MovieDetailList;

import java.util.List;

/**
 * Created by user on 03-02-2017.
 */

public class CelebrityItemDetailLoader extends AsyncTaskLoader<List<CelebrityDetailList>> {
    private static final String LOG_TAG = MovieItemDetailLoader.class.getName();
    private String mUrl ;

    public CelebrityItemDetailLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<CelebrityDetailList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        List<CelebrityDetailList> movie_detail = QueryUtils.fetchCelebrityDetailData(this.mUrl);
        return movie_detail ;
    }
}
