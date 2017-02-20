package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.data.PersonFavourites;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class MovieItemAdapter  extends RecyclerView.Adapter<MovieItemAdapter.DataHolder> implements Filterable {

    private LayoutInflater inflater ;
    private List<MovieItemList> listData = new ArrayList<>();
    private List<MovieItemList> filteredListData = new ArrayList<>();
    private itemMovieClickCallback itemClickCallback ;
    private static final String TAG = "MovieItemAdapter";
    private MovieFilter movieFilter;

    public MovieItemAdapter(Context c) {
        inflater = LayoutInflater.from(c);
    }

    public interface itemMovieClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemMovieClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
    }

    @Override
    public Filter getFilter() {
        movieFilter = new MovieFilter(this, listData);
        return  movieFilter;
    }

    private class MovieFilter extends Filter{

        private  MovieItemAdapter my_adapter;
        private List<MovieItemList> original_list;
        private List<MovieItemList> filtered_list;

        public MovieFilter(MovieItemAdapter my_adapter, List<MovieItemList> original_list) {
            this.my_adapter = my_adapter;
            this.original_list = original_list;
            this.filtered_list = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filtered_list.clear();
            FilterResults results = new FilterResults();

            if (constraint.length() == 0){
                Log.d(TAG, "here - A");
                filtered_list.clear();
                filtered_list.addAll(original_list);
                Log.d(TAG, original_list.size()+"");
            }else {
                Log.d(TAG, "here - B");
                String finalPattern = constraint.toString().toLowerCase().trim();
                for (MovieItemList movieItemList : original_list){
                    if (movieItemList.getTitle().contains(finalPattern)){
                        filtered_list.add(movieItemList);
                    }
                }
            }
            results.values = filtered_list;
            results.count = filtered_list.size();
            Log.d(TAG, results.count+"");
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData.clear();
            filteredListData.addAll((List<MovieItemList>)results.values);
        }
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
        Log.d(TAG, data.get(0).getTitle());
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        listData.clear();
    }

    @Override
    public int getItemCount() {
        if(filteredListData.isEmpty()){
            return listData.size();
        }else{
            return filteredListData.size();
        }
    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView item;
        View container ;

        public DataHolder(View viewItem){
            super(viewItem);
            item = (TextView) viewItem.findViewById(R.id.movie_lbl_item);
            container = viewItem.findViewById(R.id.movie_item_container);
            container.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.movie_item_container){
                MovieItemList item = listData.get(getAdapterPosition());
                itemClickCallback.onItemClick(item.getMovie_id());
            }
        }
    }
}
