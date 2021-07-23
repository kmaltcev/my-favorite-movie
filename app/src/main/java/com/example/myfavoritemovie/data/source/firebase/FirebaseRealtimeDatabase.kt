package com.example.myfavoritemovie.data.source.firebase

import com.example.myfavoritemovie.data.source.firebase.dto.FirebaseMovieDto
import com.example.myfavoritemovie.data.source.firebase.dto.FirebaseSeriesDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRealtimeDatabase {

    private val database by lazy { Firebase.database.reference }

    val favoriteMovies: DatabaseReference by lazy { database.child(PATH_FAVORITE_MOVIES) }
    val needToWatchMovies: DatabaseReference by lazy { database.child(PATH_NEED_TO_WATCH_MOVIES) }
    val upcomingMovies: DatabaseReference by lazy { database.child(PATH_TO_UPCOMING_MOVIES) }
    private val series: DatabaseReference by lazy { database.child(PATH_SERIES) }

    fun putFavoriteMovie(movie: FirebaseMovieDto) {
        favoriteMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    fun putNeedToWatchMovie(movie: FirebaseMovieDto) {
        needToWatchMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    fun putSeries(series: FirebaseSeriesDto) {
        this.series
            .child(series.internalId)
            .setValue(series)
    }

    suspend fun getAllMovies(): List<FirebaseMovieDto> {
        return favoriteMovies.getMovies() + needToWatchMovies.getMovies()
    }

    suspend fun getAllSeries(): List<FirebaseSeriesDto> {
        return series.getSeries()
    }

    private suspend fun DatabaseReference.getMovies(): List<FirebaseMovieDto> =
        suspendCoroutine { continuation ->
            this.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaesError: DatabaseError) {
                    continuation.resumeWithException(RuntimeException("Query was cancelled! ${databaesError.message}"))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    continuation.resume(
                        dataSnapshot
                            .children
                            .map { it.getValue(FirebaseMovieDto::class.java)!! }
                    )
                }
            })
        }

    private suspend fun DatabaseReference.getSeries(): List<FirebaseSeriesDto> =
        suspendCoroutine { continuation ->
            this.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaesError: DatabaseError) {
                    continuation.resumeWithException(RuntimeException("Query was cancelled! ${databaesError.message}"))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    continuation.resume(
                        dataSnapshot
                            .children
                            .map { it.getValue(FirebaseSeriesDto::class.java)!! }
                    )
                }
            })
        }

    fun removeFavoriteMovie(movie: FirebaseMovieDto) {
        favoriteMovies.child(movie.internalId).removeValue()
    }

    fun removeNeedToWatchMovie(movie: FirebaseMovieDto) {
        needToWatchMovies.child(movie.internalId).removeValue()
    }

    private companion object {
        const val PATH_FAVORITE_MOVIES = "favoriteMovies"
        const val PATH_NEED_TO_WATCH_MOVIES = "needToWatchMovies"
        const val PATH_SERIES = "series"
        const val PATH_TO_UPCOMING_MOVIES = "upcomingMovies"
    }
}