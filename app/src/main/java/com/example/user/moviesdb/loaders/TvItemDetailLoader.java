package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieDetailList;
import com.example.user.moviesdb.data.TvDetailList;

import java.util.List;

/**
 * Created by user on 03-02-2017.
 */

public class TvItemDetailLoader extends AsyncTaskLoader<List<TvDetailList>> {
    private static final String LOG_TAG = TvItemDetailLoader.class.getName();
    private String mUrl ;

    public TvItemDetailLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TvDetailList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }
        List<TvDetailList> tv_detail = QueryUtils.fetchTvDetailData(this.mUrl);
        return tv_detail ;
    }
}
