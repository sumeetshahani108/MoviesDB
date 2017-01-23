package com.example.user.moviesdb;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.user.moviesdb.adapters.MovieGenreAdapter;
import com.example.user.moviesdb.adapters.MovieGenreItemAdapter;
import com.example.user.moviesdb.data.MovieGenreData;
import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.loaders.MovieGenreListLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenMoviesPage extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<MovieGenreDataList>>,MovieGenreAdapter.itemClickCallback  {

    GridView myGridView;
    private static final String genre_id = "genre_id";
    private ArrayList listData ;
    private RecyclerView recView;
    private MovieGenreAdapter adapter;
    private static final String Movie_Genre_List_Url = "https://api.themoviedb.org/3/genre/movie/list?api_key=b767446da35c14841562288874f02281&language=en-US";
    private static final int MOVIE_GENRE_LIST_ID = 1;
    private static final String TAG = "HomeScreenMoviesPage";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = (ArrayList) MovieGenreData.getMovieGenreListData();
        adapter = new MovieGenreAdapter(listData, getActivity());
        adapter.setItemClickCallback(this);

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

        getLoaderManager().initLoader(MOVIE_GENRE_LIST_ID, null, this);
        return v;
    }

    @Override
    public android.support.v4.content.Loader<List<MovieGenreDataList>> onCreateLoader(int id, Bundle args) {
        return new MovieGenreListLoader(getActivity(), Movie_Genre_List_Url);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<MovieGenreDataList>> loader, List<MovieGenreDataList> data) {
        if(data != null && !data.isEmpty()){
            adapter.swap(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<MovieGenreDataList>> loader) {
        adapter.clear();
    }

    @Override
    public void onItemClick(int p) {
        Intent i = new Intent(getActivity(), GenreDetailActivity.class);
        i.putExtra("genre_id", p);
        startActivity(i);
    }
}
