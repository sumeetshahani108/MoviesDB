package com.example.user.moviesdb;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.moviesdb.adapters.MovieYoutubeVideoAdapter;
import com.example.user.moviesdb.adapters.TvYoutubeVideoAdapter;
import com.example.user.moviesdb.data.MovieYoutubeVideoList;
import com.example.user.moviesdb.data.TvYoutubeVideoList;

import java.util.List;

public class TvDetailYoutubeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TvYoutubeVideoList>>{

    private static String tv_youtube_video_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail_youtube);

        Intent myIntent = getIntent();
        int intValue  = myIntent.getIntExtra("tv_id",32811);

        tv_youtube_video_url = "https://api.themoviedb.org/3/tv/" +intValue + "/videos?api_key=b767446da35c14841562288874f02281&language=en-US";

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.tv_youtube_list);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        TvYoutubeVideoAdapter adapter=new TvYoutubeVideoAdapter(TvDetailYoutubeActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Loader<List<TvYoutubeVideoList>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<TvYoutubeVideoList>> loader, List<TvYoutubeVideoList> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<TvYoutubeVideoList>> loader) {

    }
}
