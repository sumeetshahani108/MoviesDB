package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.PersonRated;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-02-2017.
 */

public class PersonRatedAdapter extends RecyclerView.Adapter<PersonRatedAdapter.PersonRatedViewHolder>{

    private LayoutInflater inflater;
    private List<PersonRated> listData = new ArrayList<>();
    private static final String TAG = "PersonRatedAdapter";

    public PersonRatedAdapter(Context c){
        inflater = LayoutInflater.from(c);
    }

    @Override
    public PersonRatedAdapter.PersonRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_rated_item,null);
        PersonRatedAdapter.PersonRatedViewHolder personRatedViewHolder = new PersonRatedAdapter.PersonRatedViewHolder(view);
        return personRatedViewHolder;
    }

    @Override
    public void onBindViewHolder(PersonRatedAdapter.PersonRatedViewHolder holder, int position) {
        PersonRated item = listData.get(position);
        holder.movie_name.setText(item.getMovie_title());
        holder.movie_rating.setText(String.valueOf(item.getMovie_ratings()));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void getData(PersonRated data){
        if (data != null){
            listData.add(data);
            //Log.d(TAG, data.getMovie_title());
            notifyDataSetChanged();
        }
    }

    class PersonRatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView movie_name;
        TextView movie_rating;

        public PersonRatedViewHolder(View itemView){
            super(itemView);
            movie_name = (TextView)itemView.findViewById(R.id.rate_movie_title);
            movie_rating = (TextView)itemView.findViewById(R.id.movie_rating);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
