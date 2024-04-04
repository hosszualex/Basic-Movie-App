package com.example.basicmovieapp.data.models

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    val id: Int,
    val rating: Double,
    val releaseDate: String,
    val posterUrl: String,
    val runtime: Int,
    val overview: String,
    val budget: Double,
    val language: String,
    val genres: List<String>,
    var isFavorite: Boolean = false
)