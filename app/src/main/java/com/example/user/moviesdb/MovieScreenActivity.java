package com.example.user.moviesdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MovieScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MOVIESCREENACTIVITY";
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

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
        int id = item.getItemId();
        Log.d(TAG, "here");
        if(id == R.id.nav_home){
            Intent homeIntent = new Intent(MovieScreenActivity.this, HomeScreenActivity.class);
            startActivity(homeIntent);
        }else if(id == R.id.nav_movies){
            Intent moviesIntent = new Intent(MovieScreenActivity.this, MovieScreenActivity.class);
            startActivity(moviesIntent);
        }else if(id == R.id.nav_tv){
            //Toast.makeText(this, "Television", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_celebrities){

        }else if(id == R.id.nav_news){

        }else if(id == R.id.nav_personal_account){

        }else if(id == R.id.nav_personal_wishlist){

        }else if(id == R.id.nav_personal_favourites){

        }else if(id == R.id.nav_personal_rated_movies){

        }else if(id == R.id.nav_personal_notifications){

        }else if(id == R.id.nav_logout){

        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
