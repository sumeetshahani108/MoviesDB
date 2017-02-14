package com.example.user.moviesdb;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.moviesdb.adapters.CelebrityItemDetailAdapter;
import com.example.user.moviesdb.adapters.MovieItemDetailAdapter;
import com.example.user.moviesdb.data.CelebrityDetailList;
import com.example.user.moviesdb.data.MovieDetailList;
import com.example.user.moviesdb.loaders.CelebrityItemDetailLoader;
import com.example.user.moviesdb.loaders.MovieItemDetailLoader;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CelebrityDetailScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CelebrityDetailList>>{

    ImageView i;
    private Bitmap bitmap;
    private static String Celebrity_Item_Url = "";
    private static final int Celebrity_detail_screen_id = 1;
    private static final String TAG = "CelebrityDetailScreen";
    private CelebrityItemDetailAdapter adapter;
    private RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_detail_screen);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("celebrity_id", 328111);

        //i = (ImageView) findViewById(R.id.celebrity_poster_image);
        //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(i);
        Celebrity_Item_Url = "https://api.themoviedb.org/3/person/" +intValue +"?api_key=b767446da35c14841562288874f02281&language=en-US";
        recyclerView = (RecyclerView)findViewById(R.id.celebrity_detail_list);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            });
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        getLoaderManager().initLoader(Celebrity_detail_screen_id, null, this);
    }

    @Override
    public android.content.Loader<List<CelebrityDetailList>> onCreateLoader(int id, Bundle args) {
        return new CelebrityItemDetailLoader(this, Celebrity_Item_Url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<CelebrityDetailList>> loader, List<CelebrityDetailList> data) {
        if(data != null && !data.isEmpty()){
            Log.d(TAG, data+"");

            adapter = new CelebrityItemDetailAdapter(this);
            adapter.swap(data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<CelebrityDetailList>> loader) {

    }
}


