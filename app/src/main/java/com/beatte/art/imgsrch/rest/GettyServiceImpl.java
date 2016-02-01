package com.beatte.art.imgsrch.rest;

import com.beatte.art.imgsrch.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public interface GettyServiceImpl {

    @GET("?sort_order=best")
    Call<GettyImages> search(@Header("Api-Key") String apiKey, @Query("phrase") String searchText);
}
