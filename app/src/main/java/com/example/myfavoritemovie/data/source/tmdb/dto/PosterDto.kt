package com.example.myfavoritemovie.data.source.tmdb.dto

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("file_path")
    val relatedUri: String
)