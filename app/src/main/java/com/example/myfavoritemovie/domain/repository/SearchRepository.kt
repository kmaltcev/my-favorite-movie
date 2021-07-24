package com.example.myfavoritemovie.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myfavoritemovie.data.converters.buildImage
import com.example.myfavoritemovie.data.converters.toMovie
import com.example.myfavoritemovie.data.converters.toSeries
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.data.source.tmdb.TMDbApiService
import com.example.myfavoritemovie.data.source.tmdb.createTMDbAbsoluteImageUri
import com.example.myfavoritemovie.data.source.tmdb.dto.MediaDto
import com.example.myfavoritemovie.data.source.tmdb.mediaTypeIsMovie
import com.example.myfavoritemovie.data.source.tmdb.mediaTypeIsTV
import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val tmdbApiService: TMDbApiService,
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase
) {

    @RequiresApi(Build.VERSION_CODES.O)
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
            val addedMovie = upcomingMovies.find { it.externalId == firebaseUpcoming[i].externalId }
            if (addedMovie != null) {
                firebaseUpcoming[i] = addedMovie.toMovie()
            }
        }
        return firebaseUpcoming
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun searchMovies(query: String): List<Movie> {
        val medias = withContext(Dispatchers.IO) {
            tmdbApiService.multipleSearch(query).results
        }
        val movies = mutableListOf<Movie>()

        medias.forEach {
//            movies.add(it.toMovie())
            when {
                mediaTypeIsMovie(it.mediaType) ->
                    movies.add(it.toMovie())
                mediaTypeIsTV(it.mediaType) ->
                    movies.addAll(getMovieForTV(it))
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

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getMovieForTV(mediaTv: MediaDto): List<Movie> {
        val series = mediaTv.toSeries()
        val seasons = withContext(Dispatchers.IO) {
            tmdbApiService.getTvDetails(mediaTv.id!!).seasons
        }
        val map = seasons.map {
            it.toMovie(series)
        }
        return map
    }

    suspend fun searchPosters(movie: Movie): List<Image> = withContext(Dispatchers.IO) {
        if (movie.externalId == null) {
            return@withContext emptyList<Image>()
        }
        val posters: List<Image>
        if (movie.relatedSeries != null) {
            val allPosters = mutableListOf<Image>()
            movie.relatedSeries.externalId?.let { tvId ->
                allPosters.addAll(
                    movie.seasonNumber?.let {
                        tmdbApiService.getSeasonPosters(tvId, movie.seasonNumber)
                            .posters
                            .mapNotNull { dto -> buildImage(createTMDbAbsoluteImageUri(dto.relatedUri)) }
                    } ?: emptyList()
                )
                allPosters.addAll(
                    tmdbApiService.getTvPosters(tvId)
                        .posters
                        .mapNotNull { dto -> buildImage(createTMDbAbsoluteImageUri(dto.relatedUri)) }
                )
            }
            posters = allPosters.distinct()
        } else {
            posters = tmdbApiService.getPosters(movie.externalId)
                .posters
                .mapNotNull { dto -> buildImage(createTMDbAbsoluteImageUri(dto.relatedUri)) }
        }
        return@withContext posters
    }

    suspend fun searchNames(movie: Movie): List<String> {
        if (movie.relatedSeries?.externalId == null || movie.seasonNumber == null) {
            return emptyList()
        }
        val enTv = tmdbApiService.getTvDetails(movie.relatedSeries.externalId)
        val ruTv = tmdbApiService.getTvDetails(movie.relatedSeries.externalId, "ru")
        val enSeason = enTv.seasons.find { it.seasonNumber == movie.seasonNumber }!!
        val ruSeason = ruTv.seasons.find { it.seasonNumber == movie.seasonNumber }!!
        return listOf(
            enTv.name,
            ruTv.name,
            "${ruTv.name} ${ruSeason.seasonNumber}",
            "${enTv.name} ${enSeason.seasonNumber}",
            ruSeason.name,
            enSeason.name,
            "${ruTv.name} - ${ruSeason.name}",
            "${enTv.name} - ${enSeason.name}"
        ).distinct()
            .filterNot { it.isBlank() && it.length > 3 }
    }
}