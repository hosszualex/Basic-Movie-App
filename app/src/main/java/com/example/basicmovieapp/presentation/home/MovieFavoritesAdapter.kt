package com.example.basicmovieapp.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.basicmovieapp.databinding.ListItemMovieFavoriteBinding
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.presentation.IOnMovieClickListener

class MovieFavoritesAdapter(
    private val context: Context,
    private val listener: IOnMovieClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var favoriteMoviesList: List<Movie> = listOf() // todo: add diffutil
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteMovieViewHolder(
            ListItemMovieFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = favoriteMoviesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as FavoriteMovieViewHolder).bindView(favoriteMoviesList[position])

    inner class FavoriteMovieViewHolder(private val binding: ListItemMovieFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(binding) {
                imageViewPoster.load(item.posterUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}