package com.example.user.moviesdb;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.moviesdb.adapters.MovieYoutubeVideoAdapter;
import com.example.user.moviesdb.data.MovieYoutubeVideoList;

import java.util.List;

public class MovieDetailYoutubeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieYoutubeVideoList>>{

    private static String movie_youtube_video_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_youtube);

        Intent myIntent = getIntent();
        int intValue  = myIntent.getIntExtra("movie_id",32811);

        movie_youtube_video_url = "https://api.themoviedb.org/3/movie/" + intValue + "/videos?api_key=b767446da35c14841562288874f02281&language=en-US";

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        MovieYoutubeVideoAdapter adapter=new MovieYoutubeVideoAdapter(MovieDetailYoutubeActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Loader<List<MovieYoutubeVideoList>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<MovieYoutubeVideoList>> loader, List<MovieYoutubeVideoList> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<MovieYoutubeVideoList>> loader) {

    }
}
