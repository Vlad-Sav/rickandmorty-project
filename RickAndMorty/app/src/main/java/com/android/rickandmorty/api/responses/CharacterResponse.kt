package com.android.rickandmorty.api.responses

import com.android.rickandmorty.model.Characters
import com.google.gson.annotations.SerializedName

class CharacterResponse {
    @SerializedName("results")
    lateinit var characters: List<Characters>
}