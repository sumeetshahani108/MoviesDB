package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.LoaderManager ;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.user.moviesdb.adapters.MovieItemDetailAdapter;
import com.example.user.moviesdb.data.MovieDetailList;
import com.example.user.moviesdb.loaders.MovieItemDetailLoader;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieDetailScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieDetailList>>{

    ImageView i;
    private Bitmap bitmap;
    private static String Movie_Item_Url = "";
    private static final int Movie_detail_screen_id = 1;
    private static final String TAG = "MovieDetailScreen";
    private MovieItemDetailAdapter adapter;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_screen);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("movie_id", 328111);

        //i = (ImageView) findViewById(R.id.movie_poster_image);
        //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(i);
        Movie_Item_Url = "https://api.themoviedb.org/3/movie/" + intValue + "?api_key=b767446da35c14841562288874f02281&language=en-US";
        recyclerView = (RecyclerView)findViewById(R.id.movie_detail_list);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            });
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        getLoaderManager().initLoader(Movie_detail_screen_id, null, this);

    }



    @Override
    public android.content.Loader<List<MovieDetailList>> onCreateLoader(int id, Bundle args) {
        return new MovieItemDetailLoader(this, Movie_Item_Url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<MovieDetailList>> loader, List<MovieDetailList> data) {
        if(data != null && !data.isEmpty()){
            Log.d(TAG, data+"");

            adapter = new MovieItemDetailAdapter(this);
            adapter.swap(data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<MovieDetailList>> loader) {

    }
}


