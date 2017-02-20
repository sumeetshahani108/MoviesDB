package com.example.user.moviesdb;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.user.moviesdb.adapters.MovieGenreItemAdapter;
import com.example.user.moviesdb.adapters.TvGenreItemAdapter;
import com.example.user.moviesdb.data.MovieGenreItemsData;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.data.TvGenreItemsData;
import com.example.user.moviesdb.data.TvGenreItemsDataList;
import com.example.user.moviesdb.loaders.MovieGenreListItemLoader;
import com.example.user.moviesdb.loaders.TvGenreListItemLoader;

import java.util.ArrayList;
import java.util.List;

public class TvGenreDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TvGenreItemsDataList>>{

    private RecyclerView recyclerView;
    private TvGenreItemAdapter adapter;
    private ArrayList listData ;
    private static String TV_GENRE_ITEM_LIST_URL = "";
    private static final int TV_GENRE_ITEM_LIST_ID = 1 ;
    private static final String TAG = "TvGenreDetailActivity";
    private static String final_url;


    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    public TvGenreDetailActivity(){ super(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_genre_detail);
        recyclerView = (RecyclerView) findViewById(R.id.tv_genre_detail_list);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("genre_id",28);
        Log.d(TAG, intValue+"");
        TV_GENRE_ITEM_LIST_URL = "https://api.themoviedb.org/3/discover/tv?with_genres="+ intValue+"&api_key=b767446da35c14841562288874f02281&language=en-US";

        Log.d(TAG, TV_GENRE_ITEM_LIST_URL+"");

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        listData = (ArrayList) TvGenreItemsData.getTvGenreItemsDataList();
        adapter = new TvGenreItemAdapter(listData, this);
        recyclerView.setAdapter(adapter);
        getLoaderManager().restartLoader(TV_GENRE_ITEM_LIST_ID, null, this);
    }

    @Override
    public android.content.Loader<List<TvGenreItemsDataList>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, TV_GENRE_ITEM_LIST_URL);
        return new TvGenreListItemLoader(this, TV_GENRE_ITEM_LIST_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<TvGenreItemsDataList>> loader, List<TvGenreItemsDataList> data) {
        if(data != null && !data.isEmpty()){
            adapter.swap(data);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<TvGenreItemsDataList>> loader) {
        adapter.clear();
    }
}
