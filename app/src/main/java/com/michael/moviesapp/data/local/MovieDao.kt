package com.michael.moviesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movies WHERE id = :id)")
    fun isFavorite(id: Int): LiveData<Boolean>
}