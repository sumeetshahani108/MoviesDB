package com.example.user.moviesdb;

import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import com.example.user.moviesdb.data.CelebrityDetailList;
import com.example.user.moviesdb.data.CelebrityItemList;
import com.example.user.moviesdb.data.MovieDetailList;
import com.example.user.moviesdb.data.MovieGenreDataList;
import com.example.user.moviesdb.data.MovieGenreItemsDataList;
import com.example.user.moviesdb.data.MovieItemList;
import com.example.user.moviesdb.data.TvDetailList;
import com.example.user.moviesdb.data.TvGenreDataList;
import com.example.user.moviesdb.data.TvGenreItemsDataList;
import com.example.user.moviesdb.data.TvItemList;

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

    private QueryUtils() {

    }

    public static List<MovieGenreDataList> fetchMovieGenreListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request.", e);
        }

        List<MovieGenreDataList> movieGenreList = extractFeatureFromJSONMovieGenreList(jsonResponse);
        return movieGenreList;
    }

    public static List<MovieDetailList> fetchMovieDetailData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request.", e);
        }
        List<MovieDetailList> movieDetailList = extractFeatureFromJSONMovieDetailList(jsonResponse);
        return movieDetailList;
    }

    public static List<TvDetailList> fetchTvDetailData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request.", e);
        }
        List<TvDetailList> tvDetailList = extractFeatureFromJSONTvDetailList(jsonResponse);
        return tvDetailList;
    }

    public static List<MovieGenreItemsDataList> fetchMovieGenreItemListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request", e);
        }

        List<MovieGenreItemsDataList> movieGenreItemsDataLists = extractFeatureFromJSONMovieGenreItemList(jsonResponse);
        return movieGenreItemsDataLists;
    }

    public static List<MovieItemList> fetchMovieListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request", e);
        }

        List<MovieItemList> movieItemData = extractFeatureFromJSONMovieList(jsonResponse);
        return movieItemData;
    }
    public static List<TvItemList> fetchTvListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request", e);
        }

        List<TvItemList> tvItemData = extractFeatureFromJSONTvList(jsonResponse);
        return tvItemData;
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

    public static List<CelebrityItemList> fetchCelebrityListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request", e);
        }

        List<CelebrityItemList> celebrityItemData = extractFeatureFromJSONCelebrityList(jsonResponse);
        return celebrityItemData;
    }

    public static List<CelebrityDetailList> fetchCelebrityDetailData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGetRequest(url);
        } catch (IOException e) {
            Log.e(Log_Tag, "Problem Making the HTTP Request.", e);
        }
        List<CelebrityDetailList> celebrityDetailList = extractFeatureFromJSONCelebrityDetailList(jsonResponse);
        return celebrityDetailList;
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
            Log.e(Log_Tag, "Problem retrieving the JSON results.", e);
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

    private static List<MovieGenreDataList> extractFeatureFromJSONMovieGenreList(String movieGenreListJSON) {
        if (TextUtils.isEmpty(movieGenreListJSON)) {
            return null;
        }

        List<MovieGenreDataList> movieGenreList = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(movieGenreListJSON);
            JSONArray genres = baseJSONResponse.getJSONArray("genres");

            for (int i = 0; i < genres.length(); i++) {
                JSONObject currentGenre = genres.getJSONObject(i);

                int id = currentGenre.getInt("id");
                String name = currentGenre.getString("name");
                MovieGenreDataList movieGenreDataList = new MovieGenreDataList(name, id);
                movieGenreList.add(movieGenreDataList);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the MovieGenreList JSON results", e);
        }

        return movieGenreList;
    }

    private static List<MovieGenreItemsDataList> extractFeatureFromJSONMovieGenreItemList(String movieGenreListItemJSON) {
        if (TextUtils.isEmpty(movieGenreListItemJSON)) {
            return null;
        }
        List<MovieGenreItemsDataList> movieGenreItemsDataList = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(movieGenreListItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentResult = results.getJSONObject(i);

                String title = currentResult.getString("title");
                String release_date = currentResult.getString("release_date");
                String description = currentResult.getString("overview");
                String movie_poster = currentResult.getString("poster_path");
                MovieGenreItemsDataList movieGenreItemsDataLists = new MovieGenreItemsDataList(title, release_date, description, movie_poster );
                Log.d(Log_Tag, movieGenreItemsDataLists.getMovie_image() + "");
                movieGenreItemsDataList.add(movieGenreItemsDataLists);

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the MovieGenreList JSON results", e);
        }
        return movieGenreItemsDataList;
    }

    private static List<MovieItemList> extractFeatureFromJSONMovieList(String movieItemJSON) {
        if (TextUtils.isEmpty(movieItemJSON)) {
            return null;
        }
        List<MovieItemList> movieItemList = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(movieItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentResult = results.getJSONObject(i);
                String title = currentResult.getString("title");
                int id = currentResult.getInt("id");
                String poster_path = currentResult.getString("poster_path");
                MovieItemList movieItemListData = new MovieItemList(title, id, poster_path);
                movieItemList.add(movieItemListData);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the MovieGenreList JSON results", e);
        }
        return movieItemList;
    }

    private static List<TvItemList> extractFeatureFromJSONTvList(String tvItemJSON) {
        if (TextUtils.isEmpty(tvItemJSON)) {
            return null;
        }
        List<TvItemList> tvItemList = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(tvItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentResult = results.getJSONObject(i);
                String name = currentResult.getString("name");
                int id = currentResult.getInt("id");
                String poster_path = currentResult.getString("poster_path");
                TvItemList tvItemListData = new TvItemList(name, id, poster_path);
                tvItemList.add(tvItemListData);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the MovieGenreList JSON results", e);
        }
        return tvItemList;
    }


    private static List<TvDetailList> extractFeatureFromJSONTvDetailList(String tvDetailItemJSON) {
        if (TextUtils.isEmpty(tvDetailItemJSON)) {
            return null;
        }
        List<TvDetailList> tvDetail= new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(tvDetailItemJSON);
            Log.d(Log_Tag, baseJSONResponse+"");
            int id = baseJSONResponse.getInt("id");
            String tv_name = baseJSONResponse.getString("name");
            String tv_first_air_date = baseJSONResponse.getString("first_air_date");
            String tv_overview = baseJSONResponse.getString("overview");
            String tv_last_air_date = baseJSONResponse.getString("last_air_date");
            int tv_vote_average = baseJSONResponse.getInt("vote_average");
            String tv_poster = baseJSONResponse.getString("poster_path");
            int tv_no_of_episodes = baseJSONResponse.getInt("number_of_episodes");
            int tv_no_of_seasons = baseJSONResponse.getInt("number_of_seasons");
            String tv_in_production = baseJSONResponse.getString("in_production");
            TvDetailList tvDetailList = new TvDetailList(id, tv_name, tv_overview, tv_first_air_date, tv_last_air_date, tv_vote_average, tv_in_production, tv_no_of_episodes, tv_no_of_seasons,tv_poster);

            Log.d(Log_Tag,tvDetailList.getTv_overview()+"");
            Log.d(Log_Tag,tvDetailList.getTv_original_name()+"");
            tvDetail.add(tvDetailList);
        }catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the tvGenreList JSON results", e);
        }
        Log.d(Log_Tag, tvDetail+"");
        return tvDetail;
    }

    private static List<MovieDetailList> extractFeatureFromJSONMovieDetailList(String movieDetailItemJSON) {
        if (TextUtils.isEmpty(movieDetailItemJSON)) {
            return null;
        }
        List<MovieDetailList> movieDetail= new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(movieDetailItemJSON);
            Log.d(Log_Tag, baseJSONResponse+"");
            int id = baseJSONResponse.getInt("id");
            String movie_title = baseJSONResponse.getString("title");
            String movie_tagline = baseJSONResponse.getString("tagline");
            String movie_description = baseJSONResponse.getString("overview");
            String movie_release_date = baseJSONResponse.getString("release_date");
            int movie_vote_average = baseJSONResponse.getInt("vote_average");
            String poster_path = baseJSONResponse.getString("poster_path");
            int movie_runtime = baseJSONResponse.getInt("runtime");

            MovieDetailList movieDetailList = new MovieDetailList(id, movie_title, movie_description, movie_release_date, movie_vote_average, movie_runtime, movie_tagline, poster_path);
            Log.d(Log_Tag,movieDetailList.getMovie_title()+"");
            movieDetail.add(movieDetailList);
        }catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the MovieGenreList JSON results", e);
        }
        Log.d(Log_Tag, movieDetail+"");
        return movieDetail;
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
                String tv_poster = currentResult.getString("poster_path");
                TvGenreItemsDataList tvGenreItemsDataLists = new TvGenreItemsDataList(title, release_date,description,tv_poster);
                tvGenreItemsDataList.add(tvGenreItemsDataLists);

            }

        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the MovieGenreList JSON results",e);
        }
        return tvGenreItemsDataList;
    }

    private static List<CelebrityItemList> extractFeatureFromJSONCelebrityList(String celebrityItemJSON) {
        if (TextUtils.isEmpty(celebrityItemJSON)) {
            return null;
        }
        List<CelebrityItemList> celebrityItemList = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(celebrityItemJSON);
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentResult = results.getJSONObject(i);
                String name = currentResult.getString("name");
                int id = currentResult.getInt("id");
                String profile_path = currentResult.getString("profile_path");
                CelebrityItemList celebrityItemListData = new CelebrityItemList(name, id,profile_path);
                celebrityItemList.add(celebrityItemListData);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the CelebrityGenreList JSON results", e);
        }
        return celebrityItemList;
    }

    private static List<CelebrityDetailList> extractFeatureFromJSONCelebrityDetailList(String celebrityDetailItemJSON) {
        if (TextUtils.isEmpty(celebrityDetailItemJSON)) {
            return null;
        }
        List<CelebrityDetailList> celebrityDetail= new ArrayList<>();
        try{
            JSONObject baseJSONResponse = new JSONObject(celebrityDetailItemJSON);
            Log.d(Log_Tag, baseJSONResponse+"");
            String celebrity_name = baseJSONResponse.getString("name");
            String celebrity_biography = baseJSONResponse.getString("biography");
            String celebrity_birthday = baseJSONResponse.getString("birthday");
            Double celebrity_popularity = baseJSONResponse.getDouble("popularity");
            String celebrity_placeOfBirth = baseJSONResponse.getString("place_of_birth");
            String profile_path = baseJSONResponse.getString("profile_path");
            CelebrityDetailList celebrityDetailList = new CelebrityDetailList(celebrity_name, celebrity_biography, celebrity_birthday, celebrity_popularity, celebrity_placeOfBirth,profile_path);
            Log.d(Log_Tag,celebrityDetailList.getCelebrity_biography()+"");
            celebrityDetail.add(celebrityDetailList);
        }catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the CelebrityDetail JSON results", e);
        }
        Log.d(Log_Tag, celebrityDetail+"");
        return celebrityDetail;
    }

}
