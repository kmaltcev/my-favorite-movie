package com.example.myfavoritemovie.data.source.firebase

import com.google.firebase.database.Query
import com.example.myfavoritemovie.data.source.firebase.dto.FirebaseMovieDto
import com.example.myfavoritemovie.data.source.firebase.dto.FirebaseSeriesDto

interface FirebaseRealtimeDatabase {

    val goodMovies: Query
    val needToWatchMovies: Query

    fun putGoodMovie(movie: FirebaseMovieDto)
    fun putNeedToWatchMovie(movie: FirebaseMovieDto)

    fun putSeries(series: FirebaseSeriesDto)

    suspend fun getAllMovies(): List<FirebaseMovieDto>
    suspend fun getAllSeries(): List<FirebaseSeriesDto>

    fun removeGoodMovie(movie: FirebaseMovieDto)
    fun removeNeedToWatchMovie(movie: FirebaseMovieDto)
}