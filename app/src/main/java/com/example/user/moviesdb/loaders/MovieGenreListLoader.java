package com.example.user.moviesdb.loaders;

import 	android.support.v4.content.AsyncTaskLoader ;
import android.content.Context;

import com.example.user.moviesdb.QueryUtils;
import com.example.user.moviesdb.data.MovieGenreDataList;


import java.util.List;


public class MovieGenreListLoader extends AsyncTaskLoader<List<MovieGenreDataList>> {
    private static final String LOG_TAG = MovieGenreListLoader.class.getName();
    private String mUrl ;

    public MovieGenreListLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieGenreDataList> loadInBackground() {
        if(this.mUrl == null){
            return null;
        }

        List<MovieGenreDataList> genre_list = QueryUtils.fetchMovieGenreListData(this.mUrl);
        return genre_list;
    }
}
