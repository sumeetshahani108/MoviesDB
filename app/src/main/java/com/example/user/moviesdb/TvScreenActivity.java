package com.example.user.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.user.moviesdb.adapters.TvItemAdapter;
import com.example.user.moviesdb.data.TvItemList;
import com.example.user.moviesdb.loaders.TvItemLoader;

import java.util.ArrayList;
import java.util.List;

public class TvScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, android.app.LoaderManager.LoaderCallbacks<List<TvItemList>>,TvItemAdapter.itemTvClickCallback {

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private EditText editText;
    private String tv_url;
    private static final String TAG = "TvScreenActivity";
    private TvItemAdapter adapter;
    private RecyclerView recyclerView;
    private static final int TV_LIST_ID = 1;
    private ArrayList listData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_screen);

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.tvDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.tv_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.tv_textSearch);
        editText.setHintTextColor(getResources().getColor(R.color.bg_screen1));
        editText.setTextColor(getResources().getColor(R.color.bg_screen1));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "here");
                TvScreenActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.tvSpinner);
        spinner.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        spinner.setBackgroundColor(getResources().getColor(R.color.background_color));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TvScreenActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.tv_options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        /*listData = (ArrayList) TvItemList.TvItemata.getTvItemData();
        adapter = new TvItemAdapter(listData,this);
        adapter.setItemClickCallback(this);*/

        recyclerView = (RecyclerView)findViewById(R.id.tv_recycler_list);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setAdapter(adapter);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(parent.getContext(), "Selected Item is "+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        //String selected = parent.getItemAtPosition(position).toString();
        if(parent.getItemAtPosition(position).toString().equals("Popular")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            tv_url = "https://api.themoviedb.org/3/tv/popular?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(TV_LIST_ID, null, this);

        } else if (parent.getItemAtPosition(position).toString().equals("Tv Airing Today") ){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            tv_url = "https://api.themoviedb.org/3/tv/airing_today?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(TV_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Top Rated")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            tv_url = "https://api.themoviedb.org/3/tv/top_rated?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(TV_LIST_ID, null, this);

        }else if(parent.getItemAtPosition(position).toString().equals("Tv On The Air")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            tv_url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=b767446da35c14841562288874f02281&language=en-US";
            getLoaderManager().restartLoader(TV_LIST_ID, null, this);
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
        Intent intent = new Intent(this, ProfileDetailsActivity.class);
        intent.putExtra("calling_activity", ActivityConstants.ACTIVITY_3);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        Log.d(TAG, "here");
        if(id == R.id.nav_home){
            Intent homeIntent = new Intent(this, HomeScreenActivity.class);
            startActivity(homeIntent);
        }else if(id == R.id.nav_movies){
            Intent moviesIntent = new Intent(this, MovieScreenActivity.class);
            startActivity(moviesIntent);
        }else if(id == R.id.nav_tv){
            mDrawerLayout.closeDrawers();
        }else if(id == R.id.nav_celebrities){
            Intent celebrityIntent = new Intent(this, CelebrityScreenActivity.class);
            startActivity(celebrityIntent);
        }else if(id == R.id.nav_personal_account){
            Intent profileIntent = new Intent(this, ProfileDetailsActivity.class);
            profileIntent.putExtra("calling_activity", ActivityConstants.ACTIVITY_3);
            startActivity(profileIntent);
        }else if(id == R.id.nav_personal_favourites){
            Intent movie_favourites = new Intent(this, PersonFavourites.class);
            startActivity(movie_favourites);
        }else if(id == R.id.nav_personal_rated_movies){
            Intent movie_rated = new Intent(this, PersonRated.class);
            startActivity(movie_rated);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.tvDrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public android.content.Loader<List<TvItemList>> onCreateLoader(int id, Bundle args) {
        return new TvItemLoader(this, tv_url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<TvItemList>> loader, List<TvItemList> data) {
        if(data != null && !data.isEmpty()){
            adapter = new TvItemAdapter(this);
            adapter.setItemClickCallback(this);
            adapter.swap(data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<TvItemList>> loader) {
        adapter.clear();
    }

    @Override
    public void onItemClick(int p) {
        Intent i = new Intent(this, TvDetailScreenActivity.class);
        i.putExtra("tv_id",p);
        startActivity(i);
    }
}
