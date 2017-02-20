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
import com.example.user.moviesdb.data.CelebrityItemList;
import com.example.user.moviesdb.data.CelebrityItemList;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26-01-2017.
 */

public class CelebrityItemAdapter  extends RecyclerView.Adapter<CelebrityItemAdapter.DataHolder> implements Filterable{

    private LayoutInflater inflater ;
    private List<CelebrityItemList> listData = new ArrayList<>();

    private List<CelebrityItemList> filteredListData = new ArrayList<>();
    private itemCelebrityClickCallback itemClickCallback ;
    private static final String TAG = "CelebrityItemAdapter";
    private CelebrityFilter celebrityFilter;
    private Context c;

    public CelebrityItemAdapter(Context c) {
        this.c = c;
        inflater = LayoutInflater.from(c);
    }

    public interface itemCelebrityClickCallback{
        void onItemClick(int p);
    }

    public void setItemClickCallback(final itemCelebrityClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback ;
    }

    @Override
    public Filter getFilter() {
        celebrityFilter = new CelebrityFilter(this, listData);
        return  celebrityFilter;
    }

    private class CelebrityFilter extends Filter{

        private  CelebrityItemAdapter my_adapter;
        private List<CelebrityItemList> original_list;
        private List<CelebrityItemList> filtered_list;

        public CelebrityFilter(CelebrityItemAdapter my_adapter, List<CelebrityItemList> original_list) {
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
                for (CelebrityItemList CelebrityItemList : original_list){
                    if (CelebrityItemList.getName().contains(finalPattern)){
                        filtered_list.add(CelebrityItemList);
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
            filteredListData.addAll((List<CelebrityItemList>)results.values);
        }
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
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185"+item.getProfile_path()).into(holder.profileImage);

    }

    public void swap(List<CelebrityItemList> data){
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
        ImageView profileImage;

        public DataHolder(View viewItem){
            super(viewItem);
            item = (TextView) viewItem.findViewById(R.id.celebrity_lbl_item);
            profileImage = (ImageView)viewItem.findViewById(R.id.celebrity_im_item_icon);
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
