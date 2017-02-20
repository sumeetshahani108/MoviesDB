package com.example.user.moviesdb.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesdb.R;
import com.example.user.moviesdb.data.CelebrityDetailList;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by user on 03-02-2017.
 */

public class CelebrityItemDetailAdapter extends RecyclerView.Adapter<CelebrityItemDetailAdapter.CelebrityItemDataHolder>{


    private static final String TAG = "CelebrityItemDetailAdapter";
    private LayoutInflater inflater ;
    private List<CelebrityDetailList> listData  = new ArrayList<>();
    private Context c;

    public CelebrityItemDetailAdapter(Context c){
        inflater = LayoutInflater.from(c);
        this.c = c;
    }

    public void swap(List<CelebrityDetailList> data){
        if(!data.isEmpty()){
            notifyDataSetChanged();
            listData.addAll(data);
        }else {
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    @Override
    public void onBindViewHolder(CelebrityItemDataHolder holder, int position) {
        CelebrityDetailList item = listData.get(0);
        //Log.d(TAG, item.getCelebrity_description());
        holder.celebrity_name.setText(item.getCelebrity_name());
        holder.celebrity_biography.setText(item.getCelebrity_biography());
        holder.celebrity_birthday.setText(item.getCelebrity_birthday());
        holder.celebrity_popularity.setText(Double.toString(item.getCelebrity_popularity()));
        holder.celebrity_placeOfBirth.setText(item.getCelebrity_placeOfBirth());
        //holder.celebrity_image.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        Picasso.with(this.c).load("http://image.tmdb.org/t/p/w185"+item.getProfile_path()).into(holder.celebrity_image);

    }

    @Override
    public CelebrityItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.celebrity_list_view,null);
        CelebrityItemDataHolder celebrityItemDataHolder = new CelebrityItemDataHolder(view);
        return celebrityItemDataHolder ;
    }

    class CelebrityItemDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView celebrity_name ;
        TextView celebrity_biography ;
        TextView celebrity_birthday ;
        TextView celebrity_placeOfBirth ;
        TextView celebrity_popularity ;
        ImageView celebrity_image;

        public CelebrityItemDataHolder(View itemView){
            super(itemView);
            celebrity_name = (TextView)itemView.findViewById(R.id.celebrity_name);
            celebrity_biography = (TextView)itemView.findViewById(R.id.celebrity_biography);
            celebrity_birthday = (TextView)itemView.findViewById(R.id.celebrity_birthday);
            celebrity_placeOfBirth = (TextView)itemView.findViewById(R.id.celebrity_placeOfBirth);
            celebrity_popularity = (TextView)itemView.findViewById(R.id.celebrity_popularity);
            celebrity_image = (ImageView)itemView.findViewById(R.id.celebrity_poster_image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
