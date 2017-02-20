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
import com.example.user.moviesdb.data.PersonFavourites;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-02-2017.
 */

public class PersonFavouritesAdapter extends RecyclerView.Adapter<PersonFavouritesAdapter.PersonFavouritesViewHolder> {

    private LayoutInflater inflater;
    private List<PersonFavourites> listData = new ArrayList<>();
    private static final String TAG = "PersonFavouritesAdapter";
    private Context c;

    public PersonFavouritesAdapter(Context c){
        inflater = LayoutInflater.from(c);
        this.c = c;
    }

    @Override
    public PersonFavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_favourites_item,null);
        PersonFavouritesViewHolder personFavouritesViewHolder = new PersonFavouritesViewHolder(view);
        return personFavouritesViewHolder;
    }

    @Override
    public void onBindViewHolder(PersonFavouritesViewHolder holder, int position) {
        PersonFavourites item = listData.get(position);
        holder.movie_name.setText(item.getMovie_title());
        Log.d(TAG, item.getMovie_title()+"");
        Log.d(TAG, item.getMovie_poster()+"");
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185/"+item.getMovie_poster()).into(holder.movie_image);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void getData(PersonFavourites data){
        if (data != null){
            listData.add(data);
            notifyDataSetChanged();
        }
    }

    class PersonFavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView movie_name;
        ImageView movie_image;

        public PersonFavouritesViewHolder(View itemView){
            super(itemView);
            movie_image = (ImageView)itemView.findViewById(R.id.favourites_image);
            movie_name = (TextView)itemView.findViewById(R.id.favourite_movie_title);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
