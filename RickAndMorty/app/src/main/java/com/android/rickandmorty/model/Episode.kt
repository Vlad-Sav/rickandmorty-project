package com.android.rickandmorty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: String = "",
    val name: String = "",
    val air_date: String = "",
    val episode: String = ""
): Parcelable