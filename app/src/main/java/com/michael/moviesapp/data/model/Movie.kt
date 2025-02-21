package com.michael.moviesapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float,
    val overview: String,
    val original_language: String
) : Parcelable