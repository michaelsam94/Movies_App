package com.michael.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.michael.moviesapp.data.remote.MovieApi
import com.michael.moviesapp.data.local.MovieDao
import com.michael.moviesapp.data.local.MovieDatabase
import com.michael.moviesapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies_db"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieApi, movieDao: MovieDao): MovieRepository {
        return MovieRepository(apiService,movieDao)
    }
}