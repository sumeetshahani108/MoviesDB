package com.example.user.moviesdb;

import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.data.TvGenreDataList;
import com.example.user.moviesdb.data.TvGenreItemsDataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-01-2017.
 */

public class QueryUtils {
    private static final String Log_Tag = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<MovieGenreDataList> fetchMovieGenreListData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        }catch (IOException e){
            Log.e(Log_Tag, "Problem Making the HTTP Request.",e);
        }

        List<MovieGenreDataList> movieGenreList = extractFeatureFromJSONMovieGenreList(jsonResponse);
        return movieGenreList;
    }

    public static List<MovieGenreItemsDataList> fetchMovieGenreItemListData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        }catch (IOException e){
            Log.e(Log_Tag, "Problem Making the HTTP Request",e);
        }

        List<MovieGenreItemsDataList> movieGenreItemsDataLists = extractFeatureFromJSONMovieGenreItemList(jsonResponse);
        return movieGenreItemsDataLists;
    }

    public static List<MovieItemList> fetchMovieListData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        }catch (IOException e){
            Log.e(Log_Tag, "Problem Making the HTTP Request",e);
        }

        List<MovieItemList> movieItemData = extractFeatureFromJSONMovieList(jsonResponse);
        return movieItemData;
    }

    public static List<TvGenreDataList> fetchTvGenreListData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        }catch (IOException e){
            Log.e(Log_Tag, "Problem Making the HTTP Request.",e);
        }

        List<TvGenreDataList> tvGenreList = extractFeatureFromJSONTvGenreList(jsonResponse);
        return tvGenreList;
    }

    public static List<TvGenreItemsDataList> fetchTvGenreItemListData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        }catch (IOException e){
            Log.e(Log_Tag, "Problem Making the HTTP Request",e);
        }

        List<TvGenreItemsDataList> tvGenreItemsDataLists = extractFeatureFromJSONTvGenreItemList(jsonResponse);
        return tvGenreItemsDataLists;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(Log_Tag, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpGetRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(Log_Tag, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<MovieGenreDataList> extractFeatureFromJSONMovieGenreList(String movieGenreListJSON){
        if(TextUtils.isEmpty(movieGenreListJSON)){
            return null;
        }

        List<MovieGenreDataList> movieGenreList = new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(movieGenreListJSON);
            JSONArray genres = baseJSONResponse.getJSONArray("genres");

            for (int i = 0 ; i < genres.length() ; i++){
                JSONObject currentGenre = genres.getJSONObject(i);

                int id = currentGenre.getInt("id");
                String name = currentGenre.getString("name");
                MovieGenreDataList movieGenreDataList = new MovieGenreDataList(name, id);
                movieGenreList.add(movieGenreDataList);
            }

        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }

        return movieGenreList;
    }

    private static List<MovieGenreItemsDataList> extractFeatureFromJSONMovieGenreItemList(String movieGenreListItemJSON){
        if (TextUtils.isEmpty(movieGenreListItemJSON)){
            return null;
        }
        List<MovieGenreItemsDataList> movieGenreItemsDataList = new ArrayList<>();

        try{
            JSONObject baseJSONResponse = new JSONObject(movieGenreListItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for(int i = 0 ; i < results.length() ; i++){
                JSONObject currentResult = results.getJSONObject(i);

                String title = currentResult.getString("title");
                String release_date = currentResult.getString("release_date");
                String description = currentResult.getString("overview");

                MovieGenreItemsDataList movieGenreItemsDataLists = new MovieGenreItemsDataList(title, release_date,description);
                movieGenreItemsDataList.add(movieGenreItemsDataLists);

            }

        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }
        return movieGenreItemsDataList;
    }

    private static List<MovieItemList> extractFeatureFromJSONMovieList(String movieItemJSON){
        if(TextUtils.isEmpty(movieItemJSON)){
            return null;
        }
        List<MovieItemList> movieItemList = new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(movieItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0 ; i < results.length() ; i++){
                JSONObject currentResult = results.getJSONObject(i);
                String title = currentResult.getString("title");
                int id = currentResult.getInt("id");
                MovieItemList movieItemListData = new MovieItemList(title, id);
                movieItemList.add(movieItemListData);
            }
        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }
        return  movieItemList;
    }

    private static List<TvGenreDataList> extractFeatureFromJSONTvGenreList(String tvGenreListJSON) {

        if(TextUtils.isEmpty(tvGenreListJSON)){
            return null;
        }

        List<TvGenreDataList> tvGenreList = new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(tvGenreListJSON);
            JSONArray genres = baseJSONResponse.getJSONArray("genres");

            for (int i = 0 ; i < genres.length() ; i++){
                JSONObject currentGenre = genres.getJSONObject(i);

                int id = currentGenre.getInt("id");
                String name = currentGenre.getString("name");
                TvGenreDataList tvGenreDataList = new TvGenreDataList(name, id);
                tvGenreList.add(tvGenreDataList);
            }

        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }

        return tvGenreList;
    }

    private static List<TvGenreItemsDataList> extractFeatureFromJSONTvGenreItemList(String tvGenreListItemJSON){
        if (TextUtils.isEmpty(tvGenreListItemJSON)){
            return null;
        }
        List<TvGenreItemsDataList> tvGenreItemsDataList = new ArrayList<>();

        try{
            JSONObject baseJSONResponse = new JSONObject(tvGenreListItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for(int i = 0 ; i < results.length() ; i++){
                JSONObject currentResult = results.getJSONObject(i);

                String title = currentResult.getString("name");
                String release_date = currentResult.getString("first_air_date");
                String description = currentResult.getString("overview");

                TvGenreItemsDataList tvGenreItemsDataLists = new TvGenreItemsDataList(title, release_date,description);
                tvGenreItemsDataList.add(tvGenreItemsDataLists);

            }

        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }
        return tvGenreItemsDataList;
    }

}
