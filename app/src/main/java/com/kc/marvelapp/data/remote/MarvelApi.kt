package com.kc.marvelapp.data.remote

import com.kc.marvelapp.BuildConfig
import com.kc.marvelapp.models.AllCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("hash") hash : String = BuildConfig.HASH,
        @Query("ts") ts: Int = 1,
    ) : Response<AllCharactersResponse>
}