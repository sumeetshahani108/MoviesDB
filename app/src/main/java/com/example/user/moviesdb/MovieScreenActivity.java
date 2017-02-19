package com.example.user.moviesdb;

import android.content.Intent;
import android.content.res.Configuration;
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
    private ArrayList listData ;

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

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MovieScreenActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        listData = (ArrayList) MovieItemData.getMovieItemData();
        adapter = new MovieItemAdapter(listData,this);
        adapter.setItemClickCallback(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_list);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(parent.getContext(), "Selected Item is "+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        //String selected = parent.getItemAtPosition(position).toString();
        if(parent.getItemAtPosition(position).toString().equals("Now Playing")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b767446da35c14841562288874f02281&language=en-US&page=1";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        } else if (parent.getItemAtPosition(position).toString().equals("Popular") ){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/popular?api_key=b767446da35c14841562288874f02281&language=en-US&page=1";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Top Rated")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/top_rated?api_key=b767446da35c14841562288874f02281&language=en-US&page=1";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Upcoming")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            movie_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=b767446da35c14841562288874f02281&language=en-US&page=1";
            getLoaderManager().restartLoader(MOVIE_LIST_ID, null, this);
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            Intent homeIntent = new Intent(MovieScreenActivity.this, HomeScreenActivity.class);
            startActivity(homeIntent);
            finish();
        }else if(id == R.id.nav_movies){
            mDrawerLayout.closeDrawers();
        }else if(id == R.id.nav_tv){
            //Toast.makeText(this, "Television", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_celebrities){
            Intent homeIntent = new Intent(MovieScreenActivity.this, CelebrityScreenActivity.class);
            startActivity(homeIntent);
            finish();
        }else if(id == R.id.nav_news){

        }else if(id == R.id.nav_personal_account){

        }else if(id == R.id.nav_personal_favourites){

        }else if(id == R.id.nav_personal_rated_movies){

        }else if(id == R.id.nav_personal_notifications){

        }else if(id == R.id.nav_logout){

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
            Log.d(TAG, "here");
            adapter.swap(data);
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
