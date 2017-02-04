package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.TvGenreDataList;

import java.util.List;

/**
 * Created by user on 15-01-2017.
 */

public class TvGenreAdapter extends RecyclerView.Adapter<TvGenreAdapter.DataHolder>{

    private LayoutInflater inflater ;
    private List<TvGenreDataList> listData ;
    private itemClickCallback itemClickCallback ;
    private static final String TAG = "TvGenreAdapter";

    public interface itemClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
    }

    public
    TvGenreAdapter(List<TvGenreDataList> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_tv_cards,parent,false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        TvGenreDataList item = listData.get(position);
        holder.genre.setText(item.getGenre());
    }

    public void swap(List<TvGenreDataList> data){
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

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView genre ;
        View container ;

        public DataHolder(View itemView){
            super(itemView);
            genre = (TextView) itemView.findViewById(R.id.lbl_item_tv_genre);
            container = itemView.findViewById(R.id.genre_item_container1);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(v.getId() == R.id.genre_item_container1){
                TvGenreDataList item = listData.get(getAdapterPosition());
                itemClickCallback.onItemClick(item.getId());
            }
        }
    }
}
