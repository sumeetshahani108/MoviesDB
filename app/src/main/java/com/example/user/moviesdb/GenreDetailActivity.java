package com.example.user.moviesdb;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;

import android.support.annotation.StyleRes;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user.moviesdb.adapters.MovieGenreItemAdapter;
import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.data.MovieGenreItemsData;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.loaders.MovieGenreListItemLoader;

import java.util.ArrayList;
import java.util.List;

public class GenreDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieGenreItemsDataList>> {

    private RecyclerView recyclerView;
    private MovieGenreItemAdapter adapter;
    private ArrayList listData ;
    private static String Movie_Genre_Item_List_Url = "";
    private static final int MOVIE_GENRE_ITEM_LIST_ID = 1 ;
    private static final String TAG = "GenreDetailActivity";
    private static String final_url;

    public GenreDetailActivity() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        recyclerView = (RecyclerView) findViewById(R.id.genre_detail_list);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("genre_id",28);
        //Log.d(TAG, intValue+"");
        Movie_Genre_Item_List_Url = "https://api.themoviedb.org/3/genre/" + intValue + "/movies?api_key=b767446da35c14841562288874f02281&language=en-US&include_adult=false&sort_by=created_at.asc";

        Log.d(TAG, Movie_Genre_Item_List_Url+"");

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        listData = (ArrayList) MovieGenreItemsData.getMovieGenreItemListData();
        adapter = new MovieGenreItemAdapter(listData, this);
        recyclerView.setAdapter(adapter);
        getLoaderManager().restartLoader(MOVIE_GENRE_ITEM_LIST_ID, null, this);
    }

    @Override
    public android.content.Loader<List<MovieGenreItemsDataList>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, Movie_Genre_Item_List_Url);
       return new MovieGenreListItemLoader(this, Movie_Genre_Item_List_Url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<MovieGenreItemsDataList>> loader, List<MovieGenreItemsDataList> data) {
        if(data != null && !data.isEmpty()){
            adapter.swap(data);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<MovieGenreItemsDataList>> loader) {
        adapter.clear();
    }
}
