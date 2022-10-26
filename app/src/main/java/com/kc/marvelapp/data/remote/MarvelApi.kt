package com.kc.marvelapp.data.remote

import com.kc.marvelapp.BuildConfig
import com.kc.marvelapp.domain.models.AllCharactersResponse
import com.kc.marvelapp.domain.models.ComicCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("hash") hash : String = BuildConfig.HASH,
        @Query("ts") ts: Int = 1,
    ) : Response<AllCharactersResponse>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("hash") hash : String = BuildConfig.HASH,
        @Query("ts") ts: Int = 1,
    ) : Response<AllCharactersResponse>
}