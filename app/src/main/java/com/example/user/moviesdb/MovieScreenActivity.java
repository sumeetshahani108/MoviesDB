package com.example.user.moviesdb;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesdb.adapters.MovieItemAdapter;
import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.data.MovieItemData;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.loaders.MovieItemLoader;

import java.util.ArrayList;
import java.util.List;

public class MovieScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, android.app.LoaderManager.LoaderCallbacks<List<MovieItemList>>,MovieItemAdapter.itemMovieClickCallback {

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private EditText editText;
    private String movie_url;
    private static final String TAG = "MovieScreenActivity";
    private MovieItemAdapter adapter;
    private RecyclerView recyclerView;
    private static final int MOVIE_LIST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.movieDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.textSearch);
        editText.setHintTextColor(getResources().getColor(R.color.bg_screen1));
        editText.setTextColor(getResources().getColor(R.color.bg_screen1));

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        spinner.setBackgroundColor(getResources().getColor(R.color.background_color));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MovieScreenActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_list);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(parent.getContext(), "Selected Item is "+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        //String selected = parent.getItemAtPosition(position).toString();
        if(parent.getItemAtPosition(position).toString().equals("Now Playing")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        } else if (parent.getItemAtPosition(position).toString().equals("Popular") ){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/popular?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Top Rated")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/top_rated?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Upcoming")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=b767446da35c14841562288874f02281&language=en-US";
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        Intent intent = new Intent(MovieScreenActivity.this, ProfileDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        Log.d(TAG, "here");
        if(id == R.id.nav_home){
            Intent homeIntent = new Intent(this, HomeScreenActivity.class);
            startActivity(homeIntent);
            finish();
        }else if(id == R.id.nav_movies){
            mDrawerLayout.closeDrawers();
        }else if(id == R.id.nav_tv){
            Intent tvIntent = new Intent(this, TvScreenActivity.class);
            startActivity(tvIntent);
            finish();
        }else if(id == R.id.nav_celebrities){
            Intent celebIntent = new Intent(this, CelebrityScreenActivity.class);
            startActivity(celebIntent);
            finish();
        }else if(id == R.id.nav_personal_account){
            Intent profileIntent = new Intent(this, ProfileDetailsActivity.class);
            profileIntent.putExtra("calling_activity", ActivityConstants.ACTIVITY_3);
            startActivity(profileIntent);
            finish();
        }else if(id == R.id.nav_personal_favourites){
            Intent movie_favourites = new Intent(this, PersonFavourites.class);
            startActivity(movie_favourites);
        }else if(id == R.id.nav_personal_rated_movies){
            Intent movie_rated = new Intent(this, PersonRated.class);
            startActivity(movie_rated);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.movieDrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public android.content.Loader<List<MovieItemList>> onCreateLoader(int id, Bundle args) {
        return new MovieItemLoader(this, movie_url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<MovieItemList>> loader, List<MovieItemList> data) {
        if(data != null && !data.isEmpty()){
            adapter = new MovieItemAdapter(this);
            adapter.setItemClickCallback(this);
            adapter.swap(data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<MovieItemList>> loader) {
        adapter.clear();
    }

    @Override
    public void onItemClick(int p) {
        Intent i = new Intent(this, MovieDetailScreenActivity.class);
        i.putExtra("movie_id",p);
        startActivity(i);
    }
}
