package com.example.user.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.user.moviesdb.adapters.CelebrityItemAdapter;
import com.example.user.moviesdb.data.CelebrityItemData;
import com.example.user.moviesdb.data.CelebrityItemList;
import com.example.user.moviesdb.loaders.CelebrityItemLoader;

import java.util.ArrayList;
import java.util.List;

public class CelebrityScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, android.app.LoaderManager.LoaderCallbacks<List<CelebrityItemList>>,CelebrityItemAdapter.itemCelebrityClickCallback {

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private EditText editText;
    private String celebrity_url;
    private static final String TAG = "CelebrityScreenActivity";
    private CelebrityItemAdapter adapter;
    private RecyclerView recyclerView;
    private static final int CELEBRITY_LIST_ID = 1;
    private ArrayList listData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_screen);

        Log.d(TAG, "inside CelebrityScreenActivity");

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.celebrityDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.celeb_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.celeb_spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CelebrityScreenActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.celebrity_options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        listData = (ArrayList) CelebrityItemData.getCelebrityItemData();
        adapter = new CelebrityItemAdapter(listData,this);
        adapter.setItemClickCallback(this);

        recyclerView = (RecyclerView)findViewById(R.id.celeb_recycler_list);

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
        if(parent.getItemAtPosition(position).toString().equals("Latest")){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            celebrity_url = "https://api.themoviedb.org/3/search/person?api_key=b767446da35c14841562288874f02281&language=en-US&query=latest";
            getLoaderManager().restartLoader(CELEBRITY_LIST_ID, null, this);

        } else if (parent.getItemAtPosition(position).toString().equals("Popular") ){
            //Toast.makeText(parent.getContext(), "Selected Item is"+  parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            celebrity_url = "https://api.themoviedb.org/3/search/person?api_key=c&language=en-US&query=popular";
            getLoaderManager().restartLoader(CELEBRITY_LIST_ID, null, this);

        }
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
        Intent intent = new Intent(CelebrityScreenActivity.this, ProfileDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        Log.d(TAG, "here");
        if(id == R.id.nav_home){
            Intent homeIntent = new Intent(CelebrityScreenActivity.this, HomeScreenActivity.class);
            finish();
            startActivity(homeIntent);
        }else if(id == R.id.nav_movies){
            Intent moviesIntent = new Intent(CelebrityScreenActivity.this, MovieScreenActivity.class);
            startActivity(moviesIntent);
            finish();
        }else if(id == R.id.nav_tv){

        }else if(id == R.id.nav_celebrities){
            mDrawerLayout.closeDrawers();
        }else if(id == R.id.nav_news){

        }else if(id == R.id.nav_personal_account){

        }else if(id == R.id.nav_personal_favourites){

        }else if(id == R.id.nav_personal_rated_movies){

        }else if(id == R.id.nav_personal_notifications){

        }else if(id == R.id.nav_logout){

        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.celebrityDrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public android.content.Loader<List<CelebrityItemList>> onCreateLoader(int id, Bundle args) {
        return new CelebrityItemLoader(this, celebrity_url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<CelebrityItemList>> loader, List<CelebrityItemList> data) {
        if(data != null && !data.isEmpty()){
            Log.d(TAG, "here");
            adapter.swap(data);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<CelebrityItemList>> loader) {
        adapter.clear();
    }

    @Override
    public void onItemClick(int p) {
        Intent i = new Intent(this, CelebrityDetailScreenActivity.class);
        i.putExtra("celebrity_id",p);
        startActivity(i);
    }
}
