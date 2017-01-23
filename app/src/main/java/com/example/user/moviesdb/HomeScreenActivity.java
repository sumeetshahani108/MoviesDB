
package com.example.user.moviesdb;

import android.app.LoaderManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeScreenActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,HomeViewPagerItemFragment.FragmentPagerItemCallback, NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HOMESCREENACTIVITY";
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;

    private TabLayout tabarnak;
    private ViewPager pager;
    private Toolbar mToolbar;

    private static final String[] pageTitles = {
      "Movies" , "TV Shows"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabarnak = (TabLayout)findViewById(R.id.tbl_main_content);
        pager = (ViewPager)findViewById(R.id.vpg_main_content);

        setUpPagerAndTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        Intent intent = new Intent(HomeScreenActivity.this, ProfileDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_home){
            Intent homeIntent = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
            startActivity(homeIntent);
        }else if(id == R.id.nav_movies){
            Intent moviesIntent = new Intent(HomeScreenActivity.this, MovieScreenActivity.class);
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

    private void setUpPagerAndTabs(){
        tabarnak.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
        tabarnak.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabarnak.addOnTabSelectedListener(this);
        tabarnak.setupWithViewPager(pager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onPagerItemClick(String message) {
        Toast.makeText(this, message + "!", Toast.LENGTH_SHORT).show();
    }
    public static class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter (FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            //here we replicate the fragments
            //return HomeViewPagerItemFragment.getInstance(pageTitles[position]);
            switch (position){
                case 0 :
                    HomeScreenMoviesPage homeScreenMoviesPage = new HomeScreenMoviesPage();
                    return homeScreenMoviesPage;
                case 1 :
                    HomeScreenTVShowsPage homeScreenTVShowsPage = new HomeScreenTVShowsPage();
                    return homeScreenTVShowsPage;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return pageTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return pageTitles[position];
        }
    }

}
