package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieGenreDataList;

import java.util.List;

/**
 * Created by user on 15-01-2017.
 */

public class MovieGenreAdapter  extends RecyclerView.Adapter<MovieGenreAdapter.DataHolder>{

    private LayoutInflater inflater ;
    private List<MovieGenreDataList> listData ;

    public MovieGenreAdapter(List<MovieGenreDataList> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_movies_cards,parent,false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        MovieGenreDataList item = listData.get(position);
        holder.genre.setText(item.getGenre());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView genre ;
        View container ;

        public DataHolder(View itemView){
            super(itemView);
            genre = (TextView) itemView.findViewById(R.id.lbl_item_genre);
            container = itemView.findViewById(R.id.genre_item_container);
        }

        @Override
        public void onClick(View v){

        }
    }
}
