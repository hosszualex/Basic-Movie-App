package com.example.basicmovieapp.presentation

interface IOnMovieClickListener {
    fun onMovieClicked(id: Int)

    fun onToggleFavorites(id: Int)
}