package com.android.rickandmorty.api.responses

import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Episode
import com.google.gson.annotations.SerializedName

class EpisodeResponse {
    @SerializedName("results")
    lateinit var episodes: List<Episode>
}