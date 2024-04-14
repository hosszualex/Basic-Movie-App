package com.example.basicmovieapp.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterUrl: String,
    val runtime: Int,
    val overview: String,
    val budget: Double,
    val revenue: Double,
    val language: String,
    val genres: List<String>,
    var isFavourite: Boolean = false,
    var isStaffPick: Boolean = false,
)
