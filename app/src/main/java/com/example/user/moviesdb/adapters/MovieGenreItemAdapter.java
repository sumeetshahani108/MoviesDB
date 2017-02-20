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
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 17-01-2017.
 */

public class MovieGenreItemAdapter extends RecyclerView.Adapter<MovieGenreItemAdapter.ItemDataHolder> {

    private LayoutInflater inflater ;
    private static final String  TAG = "MovieGenreItemAdapter" ;
    private List<MovieGenreItemsDataList> listData ;
    private MovieGenreAdapter.itemClickCallback itemClickCallback ;
    private Context c;

    public MovieGenreItemAdapter(List<MovieGenreItemsDataList> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.c = c;
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
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185/"+ item.getMovie_image()).into(holder.movieImage);
    }

    public void swap(List<MovieGenreItemsDataList> data){
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        listData.clear();
    }

    public List<MovieGenreItemsDataList> getListData(){
        return listData;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ItemDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title ;
        TextView release_date ;
        TextView description ;
        ImageView movieImage ;

        public ItemDataHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.genre_list_movie_title);
            release_date = (TextView) itemView.findViewById(R.id.genre_list_movie_release_date);
            description = (TextView) itemView.findViewById(R.id.genre_list_movie_description);
            movieImage = (ImageView) itemView.findViewById(R.id.genre_list_image);
        }

        @Override
        public void onClick(View v){

        }

    }
}
