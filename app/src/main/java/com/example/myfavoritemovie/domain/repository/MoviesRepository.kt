package com.example.myfavoritemovie.domain.repository

import com.example.myfavoritemovie.data.repository.converters.buildFirebaseMovieDto
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.data.source.firebase.FirebaseStorageDataSource
import com.example.myfavoritemovie.data.source.firebase.dto.FirebaseMovieDto
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.StorageReferenceImage
import com.example.myfavoritemovie.domain.entity.UriImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase,
    private val firebaseStorageDataSource: FirebaseStorageDataSource
) {

    suspend fun addFavoriteMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putFavoriteMovie)
    }

    suspend fun addNeedToWatchMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putNeedToWatchMovie)
    }

    suspend fun updateFavoriteMovie(updatedMovie: Movie) {
        putMovieUse(updatedMovie, firebaseRealtimeDatabase::putFavoriteMovie)
    }

    suspend fun updateNeedToWatchMovie(updatedMovie: Movie) {
        putMovieUse(updatedMovie, firebaseRealtimeDatabase::putNeedToWatchMovie)
    }

    private suspend fun putMovieUse(movie: Movie, putMovie: (FirebaseMovieDto) -> Unit) {

        val moviePoster = if (movie.poster is UriImage
            && movie.internalId != null
        ) {
            val posterReference = withContext(Dispatchers.IO) {
                firebaseStorageDataSource.uploadImage(
                    movie.poster.reference
                )
            }

            StorageReferenceImage(posterReference)
        } else {
            movie.poster
        }

        val seriesPoster = if (movie.relatedSeries?.poster is UriImage
            && movie.relatedSeries.internalId != null
        ) {
            val posterReference = withContext(Dispatchers.IO) {
                firebaseStorageDataSource.uploadImage(
                    movie.relatedSeries.poster.reference
                )
            }

            StorageReferenceImage(posterReference)
        } else {
            movie.relatedSeries?.poster
        }

        val movieDto =
            buildFirebaseMovieDto(
                movie.copy(
                    poster = moviePoster,
                    relatedSeries = movie.relatedSeries?.copy(
                        poster = seriesPoster
                    )
                )
            )

        putMovie(movieDto)
        movieDto.relatedSeries?.let { firebaseRealtimeDatabase.putSeries(it) }
    }


    suspend fun deleteFavoriteMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeFavoriteMovie(buildFirebaseMovieDto(movie))
        deletePoster(movie)
    }

    suspend fun deleteNeedToWatchMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeNeedToWatchMovie(buildFirebaseMovieDto(movie))
        deletePoster(movie)
    }

    suspend fun deletePoster(movie: Movie) = withContext(Dispatchers.IO) {
        if (movie.poster is StorageReferenceImage) {
            movie.poster.reference.delete()
        }
    }
}