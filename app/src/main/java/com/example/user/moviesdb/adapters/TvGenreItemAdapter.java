package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.data.TvGenreItemsDataList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 17-01-2017.
 */

public class TvGenreItemAdapter extends RecyclerView.Adapter<TvGenreItemAdapter.ItemDataHolder> {

    private LayoutInflater inflater ;
    private List<TvGenreItemsDataList> listData ;
    private TvGenreAdapter.itemClickCallback itemClickCallback ;
    private Context c;

    public TvGenreItemAdapter(List<TvGenreItemsDataList> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.c = c;
    }

    @Override
    public ItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tv_genre_list_view, parent, false);
        ItemDataHolder itemDataHolder = new ItemDataHolder(view);
        return itemDataHolder;
    }

    @Override
    public void onBindViewHolder(ItemDataHolder holder, int position) {
        TvGenreItemsDataList item = listData.get(position);
        holder.title.setText(item.getTitles());
        holder.release_date.setText(item.getRelease_date());
        holder.description.setText(item.getDescription());
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185/"+ item.getTv_poster()).into(holder.tvImage);
    }

    public void swap(List<TvGenreItemsDataList> data){
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        listData.clear();
    }

    public List<TvGenreItemsDataList> getListData(){
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
        ImageView tvImage ;

        public ItemDataHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.genre_list_tv_title);
            release_date = (TextView) itemView.findViewById(R.id.genre_list_tv_release_date);
            description = (TextView) itemView.findViewById(R.id.genre_list_tv_description);
            tvImage = (ImageView) itemView.findViewById(R.id.tv_genre_list_image);
        }

        @Override
        public void onClick(View v){

        }

    }
}
