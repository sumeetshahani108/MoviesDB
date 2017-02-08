package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.CelebrityItemList;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class CelebrityItemAdapter  extends RecyclerView.Adapter<CelebrityItemAdapter.DataHolder> {

    private LayoutInflater inflater ;
    private List<CelebrityItemList> listData ;
    private itemCelebrityClickCallback itemClickCallback ;
    private static final String TAG = "CelebrityItemAdapter";

    public CelebrityItemAdapter(List<CelebrityItemList> listData,Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    public interface itemCelebrityClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemCelebrityClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
    }

    @Override
    public CelebrityItemAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.celebrity_screen_cards, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(CelebrityItemAdapter.DataHolder holder, int position) {
        CelebrityItemList item = listData.get(position);
        holder.item.setText(item.getName());
    }

    public void swap(List<CelebrityItemList> data){
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
            item = (TextView) viewItem.findViewById(R.id.celebrity_lbl_item);
            container = viewItem.findViewById(R.id.celebrity_item_container);
            container.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.celebrity_item_container){
                CelebrityItemList item = listData.get(getAdapterPosition());
                itemClickCallback.onItemClick(item.getCelebrity_id());
            }
        }
    }
}
