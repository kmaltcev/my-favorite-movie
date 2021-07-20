package com.example.myfavoritemovie.domain.repository

import com.example.myfavoritemovie.domain.entity.Movie

interface MoviesRepository {
    suspend fun addGoodMovie(movie: Movie)
    suspend fun addNeedToWatchMovie(movie: Movie)

    suspend fun updateGoodMovie(updatedMovie: Movie)
    suspend fun updateNeedToWatchMovie(updatedMovie: Movie)

    suspend fun deleteGoodMovie(movie: Movie)
    suspend fun deleteNeedToWatchMovie(movie: Movie)
    suspend fun deletePoster(movie: Movie)
}