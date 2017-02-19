package com.example.user.moviesdb.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.data.TvItemList;

import java.util.List;

public class TvItemLoader extends AsyncTaskLoader<List<TvItemList>> {
    private static final String LOG_TAG = TvItemLoader.class.getName();
    private String mUrl ;

    public TvItemLoader(Context context, String mUrl){
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TvItemList> loadInBackground() {
        if (this.mUrl == null){
            return null;
        }
        Log.d(LOG_TAG, this.mUrl+"");
        List<TvItemList> tv_list = QueryUtils.fetchTvListData(this.mUrl);
        return tv_list;
    }
}
