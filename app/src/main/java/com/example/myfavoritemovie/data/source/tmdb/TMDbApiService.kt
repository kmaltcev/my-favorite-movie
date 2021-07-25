package com.example.myfavoritemovie.data.source.tmdb

import com.example.myfavoritemovie.data.source.tmdb.dto.SearchResultDto
import com.example.myfavoritemovie.data.source.tmdb.dto.TvDetailsResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {
    @GET("search/multi")
    suspend fun multipleSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): SearchResultDto

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") tvId: Int,
        @Query("language") language: String = "en",
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): TvDetailsResultDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("region") region: String = "IL",
        @Query("api_key") apiKey: String = TMDbApiKey.API_KEY
    ): SearchResultDto
}