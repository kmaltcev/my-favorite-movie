package com.example.myfavoritemovie.domain.repository

import com.example.myfavoritemovie.domain.entity.Movie

interface MoviesRepository {
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun addNeedToWatchMovie(movie: Movie)

    suspend fun updateFavoriteMovie(updatedMovie: Movie)
    suspend fun updateNeedToWatchMovie(updatedMovie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
    suspend fun deleteNeedToWatchMovie(movie: Movie)
    suspend fun deletePoster(movie: Movie)
}