package com.android.rickandmorty.api

import com.android.rickandmorty.api.responses.CharacterResponse
import com.android.rickandmorty.api.responses.EpisodeResponse
import com.android.rickandmorty.api.responses.LocationResponse
import com.android.rickandmorty.model.Characters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(
        @Query("page") number: Int
    ): Call<CharacterResponse>

    @GET("location")
    fun getLocations(
        @Query("page") number: Int
    ): Call<LocationResponse>

    @GET("episode")
    fun getEpisodes(
        @Query("page") number: Int
    ): Call<EpisodeResponse>

    @GET("character")
    fun searchCharacters(
        @Query("name") name: String
    ): Call<CharacterResponse>

    @GET("location")
    fun searchLocations(
        @Query("name") name: String
    ): Call<LocationResponse>

    @GET("episode")
    fun searchEpisodes(
        @Query("name") name: String
    ): Call<EpisodeResponse>

    @GET("character")
    fun filterCharacters(
        @Query("status") status: String,
        @Query("gender") gender: String
    ): Call<CharacterResponse>
}