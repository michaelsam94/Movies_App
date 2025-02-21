package com.michael.moviesapp.data.remote

import com.michael.moviesapp.data.model.Movie

data class MovieResponse(
    val results: List<Movie>
)