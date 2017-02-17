package com.example.user.moviesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.LoaderManager ;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesdb.adapters.MovieItemDetailAdapter;
import com.example.user.moviesdb.data.MovieDetailList;
import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.loaders.MovieItemDetailLoader;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieDetailScreenActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieDetailList>>,MovieItemDetailAdapter.passRating{

    ImageView i;
    private Bitmap bitmap;
    private static String Movie_Item_Url = "";
    private static final int Movie_detail_screen_id = 1;
    private static final String TAG = "MovieDetailScreen";
    private MovieItemDetailAdapter adapter;
    private RecyclerView recyclerView ;
    private DatabaseReference mDatabaseRatings;
    private DatabaseReference mDatabaseFavourites;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_screen);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("movie_id", 328111);

        //i = (ImageView) findViewById(R.id.movie_poster_image);
        //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(i);
        Movie_Item_Url = "https://api.themoviedb.org/3/movie/" + intValue + "?api_key=b767446da35c14841562288874f02281&language=en-US";
        recyclerView = (RecyclerView)findViewById(R.id.movie_detail_list);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            });
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        getLoaderManager().initLoader(Movie_detail_screen_id, null, this);
        mDatabaseRatings = FirebaseDatabase.getInstance().getReference().child("ratings");
        mDatabaseFavourites = FirebaseDatabase.getInstance().getReference().child("favourites");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public android.content.Loader<List<MovieDetailList>> onCreateLoader(int id, Bundle args) {
        return new MovieItemDetailLoader(this, Movie_Item_Url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<MovieDetailList>> loader, List<MovieDetailList> data) {
        if(data != null && !data.isEmpty()){
            //Log.d(TAG, data+"");
            adapter = new MovieItemDetailAdapter(this);
            adapter.setPassRating(this);
            adapter.swap(data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<MovieDetailList>> loader) {

    }


    @Override
    public void onPostFavourites(int id) {
        final int movie_id = id;
        Log.d(TAG, "Here now");
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Log.d(TAG, "Reached Here");
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MovieDetailScreenActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.login_alert, null);
            TextView text = (TextView)mView.findViewById(R.id.text_for_login);
            Button stay_here = (Button)mView.findViewById(R.id.stay_here);
            Button go_to_login_screen = (Button)mView.findViewById(R.id.go_to_login_screen);

            stay_here.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MovieDetailScreenActivity.this, MovieDetailScreenActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            });

            go_to_login_screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MovieDetailScreenActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }else{
            final String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference newPost = mDatabaseFavourites.push();
            newPost.child("movie_id").setValue(movie_id);
            newPost.child("person_id").setValue(user_id);
        }
    }

    @Override
    public void onSubmitRating(Float rating, int id) {
        final Float my_rating = rating;
        final int movie_id = id;
        Log.d(TAG, "Here now");
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Log.d(TAG, "Reached Here");
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MovieDetailScreenActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.login_alert, null);
            TextView text = (TextView)mView.findViewById(R.id.text_for_login);
            Button stay_here = (Button)mView.findViewById(R.id.stay_here);
            Button go_to_login_screen = (Button)mView.findViewById(R.id.go_to_login_screen);

            stay_here.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MovieDetailScreenActivity.this, MovieDetailScreenActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            });

            go_to_login_screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MovieDetailScreenActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }else{
            final String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference newPost = mDatabaseRatings.push();
            newPost.child("rating").setValue(my_rating);
            newPost.child("movie_id").setValue(movie_id);
            newPost.child("person_id").setValue(user_id);
        }
    }

    @Override
    public void onClickToWatchVideos(int id) {
        Intent i = new Intent(this, MovieYoutubeActivity.class);
        i.putExtra("movie_id",id);
        startActivity(i);
    }
}


