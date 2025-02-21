package com.michael.moviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michael.moviesapp.data.model.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float,
    val overview: String,
    val original_language: String
)

fun FavoriteMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        poster_path = this.poster_path,
        release_date = this.release_date,
        vote_average = this.vote_average,
        overview = this.overview,
        original_language = this.original_language
    )
}