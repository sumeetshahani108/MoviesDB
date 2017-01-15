package com.example.user.moviesdb;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeScreenTVShowsPage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_tv_shows_page, container, false);
        return v;
    }
}
