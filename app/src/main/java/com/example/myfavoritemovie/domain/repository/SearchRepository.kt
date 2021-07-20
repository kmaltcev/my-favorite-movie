package com.example.myfavoritemovie.domain.repository

import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie

interface SearchRepository {
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun searchPosters(movie: Movie): List<Image>
    suspend fun searchNames(movie: Movie): List<String>
}