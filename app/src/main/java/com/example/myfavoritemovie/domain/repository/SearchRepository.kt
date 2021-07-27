package com.example.myfavoritemovie.domain.repository

import com.example.myfavoritemovie.data.converters.toMovie
import com.example.myfavoritemovie.data.converters.toSeries
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.data.source.tmdb.TMDbApiService
import com.example.myfavoritemovie.data.source.tmdb.dto.MediaDto
import com.example.myfavoritemovie.data.source.tmdb.mediaTypeIsMovie
import com.example.myfavoritemovie.data.source.tmdb.mediaTypeIsTV
import com.example.myfavoritemovie.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val tmdbApiService: TMDbApiService,
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase
) {

    suspend fun searchUpcomingMovies(region: String): List<Movie> {
        val tmdbUpcoming = withContext(Dispatchers.IO) {
            tmdbApiService.getUpcomingMovies(region).results
        }

        val firebaseUpcoming = mutableListOf<Movie>()
        tmdbUpcoming.forEach {
            firebaseUpcoming.add(it.toMovie())
        }

        val upcomingMovies = firebaseRealtimeDatabase.getAllMovies()
            .filter { it.externalId != null }

        for (i in firebaseUpcoming.indices) {
            val addedMovie = upcomingMovies.find {
                it.externalId == firebaseUpcoming[i].externalId
            }
            if (addedMovie != null) {
                firebaseUpcoming[i] = addedMovie.toMovie()
            }
        }
        return firebaseUpcoming
    }


    suspend fun searchMovies(query: String): List<Movie> {
        val medias = withContext(Dispatchers.IO) {
            tmdbApiService.multipleSearch(query).results
        }
        val movies = mutableListOf<Movie>()

        medias.forEach {
            when {
                mediaTypeIsMovie(it.mediaType) ->
                    movies.add(it.toMovie())
                mediaTypeIsTV(it.mediaType) ->
                    movies.addAll(getMovieForSerie(it))
            }
        }

        val addedMovies = firebaseRealtimeDatabase.getAllMovies()
            .filter { it.externalId != null }

        val addedSeries = firebaseRealtimeDatabase.getAllSeries()
            .filter { it.externalId != null }

        for (i in movies.indices) {
            val addedMovie = addedMovies.find { it.externalId == movies[i].externalId }
            if (addedMovie != null) {
                movies[i] = addedMovie.toMovie()
            } else {
                movies[i].relatedSeries?.let { relatedSeries ->
                    val addedSerie = addedSeries.find { it.externalId == relatedSeries.externalId }
                    addedSerie?.let {
                        movies[i] = movies[i].copy(
                            relatedSeries = it.toSeries()
                        )
                    }
                }
            }
        }
        return movies
    }

    private suspend fun getMovieForSerie(mediaTv: MediaDto): List<Movie> {
        val series = mediaTv.toSeries()
        val seasons = withContext(Dispatchers.IO) {
            tmdbApiService.getTvDetails(mediaTv.id!!).seasons
        }
        return seasons.map {
            it.toMovie(series)
        }
    }
}