package com.e.myapplication.db.themoviedb;

import com.e.myapplication.db.themoviedb.dto.DataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMoviesDao {

    @GET("/3/{request}/{tv-movie}")
    Call<DataModel> getData(
            @Path("request") String request,
            @Path("tv-movie") String url,
            @Query("api_key") String apiKey,
            @Query("language") String laguage,
            @Query("query") String name,
            @Query("primary_release_date.gte") String primaryReleaseDateGte,
            @Query("primary_release_date.lte") String primaryReleaseDateLte
    );
}

