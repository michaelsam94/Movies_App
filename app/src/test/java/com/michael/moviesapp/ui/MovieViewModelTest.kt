package com.michael.moviesapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.michael.moviesapp.data.ErrorType
import com.michael.moviesapp.data.model.Movie
import com.michael.moviesapp.data.model.ResultState
import com.michael.moviesapp.data.repository.MovieRepository
import com.michael.moviesapp.ui.viewmodel.MovieViewModel
import com.michael.moviesapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var repository: MovieRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * ✅ Test case for success state
     */
    @Test
    fun `fetchMovies should update live data with movie list when successful`() = runTest {
        // Given
        val mockMovies = listOf(
            Movie(1, "Movie 1", "Overview 1", "poster1.jpg", 8.0f, "", "en"),
            Movie(2, "Movie 2", "Overview 2", "poster2.jpg", 7.5f, "", "es")
        )

        val resultState = ResultState.Success(mockMovies)
        val liveData = MutableLiveData<ResultState<List<Movie>>>()
        liveData.value = resultState

        // ✅ Fix: Ensure repository returns `ResultState` but ViewModel exposes only `List<Movie>`
        `when`(repository.getMovies("", 1)).thenReturn(liveData.value)

        // ✅ Fix: Observe LiveData matching ViewModel type (List<Movie>)
        val observer = mock(Observer::class.java) as Observer<List<Movie>>
        viewModel.movies.observeForever(observer)

        // When
        viewModel.fetchMovies("")
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutines finish

        // Then
        verify(observer).onChanged(mockMovies) // ✅ Expected: ViewModel should emit `List<Movie>`
        assertEquals(mockMovies, viewModel.movies.getOrAwaitValue()) // ✅ Match expected output

        // Cleanup
        viewModel.movies.removeObserver(observer)
    }


    /**
     * ✅ Test case for empty movie list
     */
    @Test
    fun `fetchMovies should handle empty movie list`() = runTest {
        // Given
        val resultState = ResultState.Success(emptyList<Movie>()) // Empty list case
        val liveData = MutableLiveData<ResultState<List<Movie>>>()
        liveData.value = resultState

        `when`(repository.getMovies("", 1)).thenReturn(liveData.value)

        val observer = mock(Observer::class.java) as Observer<List<Movie>>
        viewModel.movies.observeForever(observer)

        // When
        viewModel.fetchMovies("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(observer).onChanged(emptyList()) // ✅ Ensure empty list is emitted
        assertEquals(emptyList<Movie>(), viewModel.movies.getOrAwaitValue())

        // Cleanup
        viewModel.movies.removeObserver(observer)
    }



    /**
     * ✅ Test case for error state
     */
    @Test
    fun `fetchMovies should handle error state correctly`() = runTest {
        // Given
        val error = ErrorType.ServerError
        val resultState = ResultState.Error(error)
        val liveData = MutableLiveData<ResultState<List<Movie>>>()
        liveData.value = resultState

        `when`(repository.getMovies("", 1)).thenReturn(liveData.value)

        val observer = mock<Observer<List<Movie>>>()
        viewModel.movies.observeForever(observer)

        // When
        viewModel.fetchMovies("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val captor = argumentCaptor<List<Movie>>()
        verify(observer, never()).onChanged(captor.capture()) // ✅ Ensures correct type
        assertEquals(0, captor.allValues.size) // ✅ No data should be emitted

        // Cleanup
        viewModel.movies.removeObserver(observer)
    }







}
