package com.example.user.moviesdb.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieDetailList;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by user on 03-02-2017.
 */

public class MovieItemDetailAdapter extends RecyclerView.Adapter<MovieItemDetailAdapter.MovieItemDataHolder>{


    private static final String TAG = "MovieItemDetailAdapter";
    private LayoutInflater inflater ;
    private List<MovieDetailList> listData  = new ArrayList<>();

    public MovieItemDetailAdapter(Context c){
        inflater = LayoutInflater.from(c);
    }

    public void swap(List<MovieDetailList> data){
        if(!data.isEmpty()){
            Log.d(TAG, data.get(0).getMovie_title()+"");
            notifyDataSetChanged();
            listData.addAll(data);
        }else {
            Log.d(TAG, "Null Pointer Exception");
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    @Override
    public void onBindViewHolder(MovieItemDataHolder holder, int position) {
        MovieDetailList item = listData.get(0);
        //Log.d(TAG, item.getMovie_description());
        holder.movie_title.setText(item.getMovie_title());
        holder.movie_description.setText(item.getMovie_description());
        holder.movie_release_date.setText(item.getRelease_date());
        holder.movie_vote_average.setText(Integer.toString(item.getVote_average()));
        holder.movie_image.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        //Picasso.with(getApplicationContext()).load("").into(holder.movie_image);
    }

    @Override
    public MovieItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_view,null);
        MovieItemDataHolder movieItemDataHolder = new MovieItemDataHolder(view);
        return movieItemDataHolder ;
    }

    class MovieItemDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView movie_title ;
        TextView movie_description ;
        TextView movie_release_date ;
        TextView movie_vote_average ;
        ImageView movie_image;
        private RatingBar ratingBar;
        private Button submitRating;

        public MovieItemDataHolder(View itemView){
            super(itemView);
            movie_title = (TextView)itemView.findViewById(R.id.movie_title);
            movie_description = (TextView)itemView.findViewById(R.id.movie_description);
            movie_release_date = (TextView)itemView.findViewById(R.id.movie_release_date);
            movie_vote_average = (TextView)itemView.findViewById(R.id.movie_vote_average);
            movie_image = (ImageView)itemView.findViewById(R.id.movie_poster_image);
            ratingBar = (RatingBar)itemView.findViewById(R.id.rating_bar);
            submitRating = (Button)itemView.findViewById(R.id.rating_button);
            submitRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRating();
                }
            });
        }

        public void postRating(){
            String rating_value = String.valueOf(ratingBar.getRating());
            Log.d(TAG, rating_value);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
