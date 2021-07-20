package com.example.myfavoritemovie.data.source.tmdb.dto

data class TvDetailsResultDto(
    val name: String,
    val seasons: List<SeasonDto>
)