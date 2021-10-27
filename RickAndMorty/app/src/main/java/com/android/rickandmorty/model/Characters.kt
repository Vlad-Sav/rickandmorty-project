package com.android.rickandmorty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Characters (
    val id: String = "",
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val image: String = ""
): Parcelable
