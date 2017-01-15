package com.example.user.moviesdb;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.user.moviesdb.adapters.MovieGenreAdapter;
import com.example.user.moviesdb.data.MovieGenreData;

import java.util.ArrayList;

public class HomeScreenMoviesPage extends Fragment {

    GridView myGridView;
    private ArrayList listData ;
    private RecyclerView recView;
    private MovieGenreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = (ArrayList) MovieGenreData.getMovieGenreListData();
        adapter = new MovieGenreAdapter(listData, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_movies_page, container, false);
        recView = (RecyclerView) v.findViewById(R.id.rec_list);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else{
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        recView.setAdapter(adapter);
        return v;
    }
}
