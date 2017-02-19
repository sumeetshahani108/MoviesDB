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

public class TvGenreDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TvGenreItemsDataList>>, NavigationView.OnNavigationItemSelectedListener{

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

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.tvGenreDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.tv_genre_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()) {
            case R.id.view_profile:
                Log.d(TAG, "inside switch");
                viewProfile();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void viewProfile() {
        Log.d(TAG, "inside view profile");
        Intent intent = new Intent(this, ProfileDetailsActivity.class);
        intent.putExtra("calling_activity", ActivityConstants.ACTIVITY_3);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        Log.d(TAG, "here");
        if(id == R.id.nav_home){
            finish();
        }else if(id == R.id.nav_movies){
            Intent moviesIntent = new Intent(this, MovieScreenActivity.class);
            startActivity(moviesIntent);
        }else if(id == R.id.nav_tv){
            //Toast.makeText(this, "Television", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_celebrities){
            Intent celebrityIntent = new Intent(this, CelebrityScreenActivity.class);
            startActivity(celebrityIntent);

        }else if(id == R.id.nav_news){

        }else if(id == R.id.nav_personal_account){

        }else if(id == R.id.nav_personal_favourites){

        }else if(id == R.id.nav_personal_rated_movies){

        }else if(id == R.id.nav_personal_notifications){

        }else if(id == R.id.nav_logout){

        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.tvGenreDrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
