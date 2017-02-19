package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.data.TvItemList;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class TvItemAdapter  extends RecyclerView.Adapter<TvItemAdapter.DataHolder> {

    private LayoutInflater inflater ;
    private List<TvItemList> listData ;
    private itemTvClickCallback itemClickCallback ;
    private static final String TAG = "TvItemAdapter";

    public TvItemAdapter(List<TvItemList> listData,Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    public interface itemTvClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemTvClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
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
    }

    public void swap(List<TvItemList> data){
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
            item = (TextView) viewItem.findViewById(R.id.tv_lbl_item);
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
