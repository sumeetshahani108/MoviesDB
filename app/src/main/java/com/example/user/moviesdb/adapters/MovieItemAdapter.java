package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieItemList;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class MovieItemAdapter  extends RecyclerView.Adapter<MovieItemAdapter.DataHolder> {

    private LayoutInflater inflater ;
    private List<MovieItemList> listData ;
    private static final String TAG = "MovieItemAdapter";

    public MovieItemAdapter(List<MovieItemList> listData,Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public MovieItemAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movies_screen_cards, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(MovieItemAdapter.DataHolder holder, int position) {
        MovieItemList item = listData.get(position);
        holder.item.setText(item.getTitle());
    }

    public void swap(List<MovieItemList> data){
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

        TextView item;
        View container ;

        public DataHolder(View viewItem){
            super(viewItem);
            item = (TextView) viewItem.findViewById(R.id.movie_lbl_item);
            container = viewItem.findViewById(R.id.movie_item_container);

        }
        @Override
        public void onClick(View v) {

        }
    }
}
