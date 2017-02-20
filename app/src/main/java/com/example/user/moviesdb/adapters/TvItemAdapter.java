package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.TvItemList;
import com.example.user.moviesdb.data.TvItemList;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class TvItemAdapter  extends RecyclerView.Adapter<TvItemAdapter.DataHolder> implements Filterable{

    private LayoutInflater inflater ;
    private List<TvItemList> listData = new ArrayList<>();
    private List<TvItemList> filteredListData = new ArrayList<>();
    private itemTvClickCallback itemClickCallback ;
    private static final String TAG = "TvItemAdapter";
    private TvFilter tvFilter;
    private Context c;

    public TvItemAdapter(Context c) {
        this.c = c;
        inflater = LayoutInflater.from(c);
    }

    public interface itemTvClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemTvClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
    }

    @Override
    public Filter getFilter() {
        tvFilter = new TvFilter(this, listData);
        return  tvFilter;
    }

    private class TvFilter extends Filter{

        private  TvItemAdapter my_adapter;
        private List<TvItemList> original_list;
        private List<TvItemList> filtered_list;

        public TvFilter(TvItemAdapter my_adapter, List<TvItemList> original_list) {
            this.my_adapter = my_adapter;
            this.original_list = original_list;
            this.filtered_list = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filtered_list.clear();
            FilterResults results = new FilterResults();

            if (constraint.length() == 0){
                Log.d(TAG, "here - 1 ");
                filtered_list.clear();
                filtered_list.addAll(original_list);
            }else {
                Log.d(TAG, "here - 2 ");
                String finalPattern = constraint.toString().toLowerCase().trim();
                for (TvItemList TvItemList : original_list){
                    if (TvItemList.getName().contains(finalPattern)){
                        filtered_list.add(TvItemList);
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
            filteredListData.addAll((List<TvItemList>)results.values);
        }
    }


    @Override
    public TvItemAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tv_screen_cards, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(TvItemAdapter.DataHolder holder, int position) {
        TvItemList item = listData.get(position);
        holder.item.setText(item.getName());
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185"+item.getImage_path()).into(holder.imageView);
    }

    public void swap(List<TvItemList> data){
        Log.d(TAG, data.get(0).getName());
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
        ImageView imageView;
        View container ;

        public DataHolder(View viewItem){
            super(viewItem);
            item = (TextView) viewItem.findViewById(R.id.tv_lbl_item);
            imageView = (ImageView)viewItem.findViewById(R.id.tv_im_item_icon);
            container = viewItem.findViewById(R.id.tv_item_container);
            container.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tv_item_container){
                TvItemList item = listData.get(getAdapterPosition());
                itemClickCallback.onItemClick(item.getTv_id());
            }
        }
    }
}
