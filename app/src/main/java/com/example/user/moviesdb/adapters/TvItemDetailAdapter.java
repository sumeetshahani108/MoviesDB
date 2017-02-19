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
import com.example.user.moviesdb.data.TvDetailList;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by user on 03-02-2017.
 */

public class TvItemDetailAdapter extends RecyclerView.Adapter<TvItemDetailAdapter.TvItemDataHolder>{


    private static final String TAG = "TvItemDetailAdapter";
    private LayoutInflater inflater ;
    private passRating passRating;
    private List<TvDetailList> listData  = new ArrayList<>();

    public TvItemDetailAdapter(Context c){
        inflater = LayoutInflater.from(c);
    }

    public interface passRating{
        void onSubmitRating(Float rating, String tv_name, String tv_image);
        void onPostFavourites(String tv_name, String tv_image);
        void onClickToWatchVideos(int id);
    }

    public void setPassRating(final passRating passRating){
        this.passRating = passRating ;
    }

    public void swap(List<TvDetailList> data){
        if(!data.isEmpty()){
            Log.d(TAG, data.get(0).getTv_original_name()+"");
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
    public void onBindViewHolder(TvItemDataHolder holder, int position) {
        TvDetailList item = listData.get(0);
        //Log.d(TAG, item.getMovie_description());
        holder.tv_name.setText(item.getTv_original_name());
        holder.tv_overview.setText(item.getTv_overview());
        holder.tv_first_air_date.setText(item.getTv_first_air_date());
        holder.tv_last_air_date.setText(item.getTv_last_air_date());
        holder.tv_vote_average.setText(Integer.toString(item.getVote_average()) + "/10");
        holder.tv_no_of_episodes.setText(Integer.toString(item.getTv_no_of_episodes()));
        holder.tv_no_of_seasons.setText(Integer.toString(item.getTv_no_of_seasons()));
        holder.tv_in_production.setText(item.getTv_in_production());
        holder.tv_poster.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        //Picasso.with(getApplicationContext()).load("").into(holder.movie_image);
    }

    @Override
    public TvItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tv_list_view,null);
        TvItemDataHolder tvItemDataHolder = new TvItemDataHolder(view);
        return tvItemDataHolder ;
    }

    class TvItemDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name ;
        TextView tv_overview;
        TextView tv_first_air_date ;
        TextView tv_last_air_date ;
        TextView tv_vote_average ;
        ImageView tv_poster;
        TextView tv_no_of_seasons ;
        TextView tv_in_production ;
        TextView tv_no_of_episodes;
        private RatingBar tvRatingBar;
        private Button tvsubmitRating;
        private ProgressDialog mProgress;
        private Button tvFavourites;
        private Button tvVideos;

        public TvItemDataHolder(View itemView){
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_overview = (TextView)itemView.findViewById(R.id.tv_overview);
            tv_first_air_date = (TextView)itemView.findViewById(R.id.tv_first_air_date);
            tv_last_air_date = (TextView)itemView.findViewById(R.id.tv_last_air_date);
            tv_vote_average = (TextView)itemView.findViewById(R.id.tv_vote_average);
            tv_poster = (ImageView)itemView.findViewById(R.id.tv_poster);
            tvRatingBar = (RatingBar)itemView.findViewById(R.id.tv_rating_bar);
            tv_no_of_seasons = (TextView) itemView.findViewById(R.id.tv_no_of_seasons);
            tv_no_of_episodes = (TextView) itemView.findViewById(R.id.tv_no_of_episodes);
            tv_in_production = (TextView) itemView.findViewById(R.id.tv_in_production);
            tvsubmitRating = (Button)itemView.findViewById(R.id.tv_rating_button);
            tvsubmitRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postRating();
                }

            });
            tvFavourites = (Button)itemView.findViewById(R.id.tv_favourites);
            tvFavourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postFavourites();
                }
            });
            tvVideos = (Button)itemView.findViewById(R.id.tv_videos_button);
            tvVideos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchVideos();
                }
            });
        }

        public void postFavourites(){
            TvDetailList item = listData.get(getAdapterPosition());
            passRating.onPostFavourites(item.getTv_original_name(), item.getTv_poster());
        }

        public void postRating(){
            Float rating_value = tvRatingBar.getRating();
            //Log.d(TAG, rating_value+"");
            TvDetailList item = listData.get(getAdapterPosition());
            passRating.onSubmitRating(rating_value, item.getTv_original_name(), item.getTv_poster());
        }

        public void watchVideos(){
            TvDetailList item = listData.get(getAdapterPosition());
            passRating.onClickToWatchVideos(item.getId());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
