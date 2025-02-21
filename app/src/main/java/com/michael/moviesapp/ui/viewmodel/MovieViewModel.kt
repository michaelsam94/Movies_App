package com.michael.moviesapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michael.moviesapp.data.ErrorType
import com.michael.moviesapp.data.local.FavoriteMovie
import com.michael.moviesapp.data.model.Movie
import com.michael.moviesapp.data.repository.MovieRepository
import com.michael.moviesapp.data.model.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<ErrorType?>()
    val error: LiveData<ErrorType?> get() = _error

    private var currentPage = 1
    private var isFetching = false

    fun fetchMovies(apiKey: String) {
        if (isFetching) return

        viewModelScope.launch {
            _isLoading.value = true
            isFetching = true

            when (val result = repository.getMovies(apiKey, currentPage)) {
                is ResultState.Success -> {
                    _isLoading.value = false
                    val updatedList = (_movies.value ?: emptyList()) + result.data
                    _movies.value = updatedList
                    currentPage++
                    _error.value = null // Clear error if successful
                }
                is ResultState.Error -> {
                    _isLoading.value = false
                    _error.value = result.errorType // Set error to be displayed
                }

                ResultState.Loading -> _isLoading.value = true
            }

            _isLoading.value = false
            isFetching = false
        }
    }


    fun getFavorites(): LiveData<List<FavoriteMovie>> = repository.getFavoriteMovies()

    fun addFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.addFavorite(movie)
        }
    }

    fun removeFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
        }
    }

    fun isFavorite(movieId: Int): LiveData<Boolean> {
        return repository.isFavorite(movieId)
    }
}