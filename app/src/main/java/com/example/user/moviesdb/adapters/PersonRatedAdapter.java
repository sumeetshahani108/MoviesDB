package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.PersonRated;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-02-2017.
 */

public class PersonRatedAdapter extends RecyclerView.Adapter<PersonRatedAdapter.PersonRatedViewHolder>{

    private LayoutInflater inflater;
    private List<PersonRated> listData = new ArrayList<>();
    private static final String TAG = "PersonRatedAdapter";
    private Context c;

    public PersonRatedAdapter(Context c){
        this.c = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public PersonRatedAdapter.PersonRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_rated_item,null);
        PersonRatedAdapter.PersonRatedViewHolder personRatedViewHolder = new PersonRatedAdapter.PersonRatedViewHolder(view);
        return personRatedViewHolder;
    }

    @Override
    public void onBindViewHolder(PersonRatedAdapter.PersonRatedViewHolder holder, int position) {
        PersonRated item = listData.get(position);
        holder.movie_name.setText(item.getMovie_title());
        holder.movie_rating.setText(Integer.toString(item.getRating())+ "/5");
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185/"+item.getMovie_poster()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void getData(PersonRated data){
        if (data != null){
            listData.add(data);
            //Log.d(TAG, data.getMovie_title());
            notifyDataSetChanged();
        }
    }

    class PersonRatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView movie_name;
        TextView movie_rating;
        ImageView imageView;

        public PersonRatedViewHolder(View itemView){
            super(itemView);
            movie_name = (TextView)itemView.findViewById(R.id.rate_movie_title);
            imageView = (ImageView) itemView.findViewById(R.id.rate_movie_image);
            movie_rating = (TextView)itemView.findViewById(R.id.movie_rating);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
