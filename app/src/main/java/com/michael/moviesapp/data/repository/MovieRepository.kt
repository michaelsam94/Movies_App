package com.michael.moviesapp.data.repository

import androidx.lifecycle.LiveData
import com.michael.moviesapp.data.ErrorType
import com.michael.moviesapp.data.model.Movie
import com.michael.moviesapp.data.model.ResultState
import com.michael.moviesapp.data.local.FavoriteMovie
import com.michael.moviesapp.data.local.MovieDao
import com.michael.moviesapp.data.remote.MovieApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao
) {
    suspend fun getMovies(apiKey: String, page: Int): ResultState<List<Movie>> {
        return try {
            val response = api.getTopMovies(apiKey,page =page)
            ResultState.Success(response.results)
        } catch (e: IOException) { // No internet
            ResultState.Error(ErrorType.NoInternet)
        } catch (e: HttpException) { // Server error
            ResultState.Error(ErrorType.ServerError)
        } catch (e: Exception) { // Unknown error
            ResultState.Error(ErrorType.Unknown(e.localizedMessage ?: "Unknown error"))
        }
    }

    suspend fun addFavorite(movie: FavoriteMovie) {
        dao.insertFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        dao.deleteFavorite(movie)
    }

    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>> = dao.getFavoriteMovies()

    fun isFavorite(id: Int): LiveData<Boolean> {
        return dao.isFavorite(id)
    }
}
