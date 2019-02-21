package net.artemkv.marvelserver.marvelconnector;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Date;

interface MarvelApi {
    @GET("/v1/public/creators")
    Call<CreatorDataWrapper> listCreators(
        @Query("limit") int limit,
        @Query("offset") int offset,
        @Query("ts") String ts,
        @Query("apikey") String apiKey,
        @Query("hash") String hash,
        @Query("orderBy") String orderBy,
        @Query("modifiedSince") Date modifiedSince);
}
