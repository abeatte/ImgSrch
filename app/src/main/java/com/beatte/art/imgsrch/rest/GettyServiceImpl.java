package com.beatte.art.imgsrch.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public interface GettyServiceImpl {

    @Headers("Api-Key: <YOUR KEY HERE>")
    @GET("?sort_order=best")
    Call<GettyImages> search(@Query("phrase") String searchText);
}
