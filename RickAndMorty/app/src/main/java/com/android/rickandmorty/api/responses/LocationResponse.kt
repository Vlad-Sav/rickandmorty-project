package com.android.rickandmorty.api.responses

import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Location
import com.google.gson.annotations.SerializedName

class LocationResponse {
    @SerializedName("results")
    lateinit var locations: List<Location>
}