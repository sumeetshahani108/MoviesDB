package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;

import java.util.List;

/**
 * Created by user on 17-01-2017.
 */

public class MovieGenreItemAdapter extends RecyclerView.Adapter<MovieGenreItemAdapter.ItemDataHolder> {

    private LayoutInflater inflater ;
    private List<MovieGenreItemsDataList> listData ;
    private MovieGenreAdapter.itemClickCallback itemClickCallback ;

    public MovieGenreItemAdapter(List<MovieGenreItemsDataList> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public ItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.genre_list_view, parent, false);
        ItemDataHolder itemDataHolder = new ItemDataHolder(view);
        return itemDataHolder;
    }

    @Override
    public void onBindViewHolder(ItemDataHolder holder, int position) {
        MovieGenreItemsDataList item = listData.get(position);
        holder.title.setText(item.getTitles());
        holder.release_date.setText(item.getRelease_date());
        holder.description.setText(item.getDescription());
    }

    public void swap(List<MovieGenreItemsDataList> data){
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        listData.clear();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ItemDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title ;
        TextView release_date ;
        TextView description ;

        public ItemDataHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.genre_list_movie_title);
            release_date = (TextView) itemView.findViewById(R.id.genre_list_movie_release_date);
            description = (TextView) itemView.findViewById(R.id.genre_list_movie_description);
        }

        @Override
        public void onClick(View v){

        }

    }
}
