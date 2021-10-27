package com.android.rickandmorty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val dimension: String = ""
): Parcelable