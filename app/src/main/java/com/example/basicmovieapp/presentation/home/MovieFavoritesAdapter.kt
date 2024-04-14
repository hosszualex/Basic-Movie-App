package com.example.basicmovieapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.basicmovieapp.databinding.ListItemMovieFavoriteBinding
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.presentation.core.IOnMovieClickListener

class MovieFavoritesAdapter(
    private val listener: IOnMovieClickListener,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffUtil =
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean {
                return oldItem == newItem
            }
        }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun refreshData(data: List<Movie>) {
        asyncListDiffer.submitList(data.map { it.copy() })
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return FavoriteMovieViewHolder(
            ListItemMovieFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) = (holder as FavoriteMovieViewHolder).bindView(asyncListDiffer.currentList[position])

    inner class FavoriteMovieViewHolder(private val binding: ListItemMovieFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(binding) {
                imageViewPoster.load(item.posterUrl) {
                    crossfade(true)
                }
                root.setOnClickListener { listener.onMovieClicked(item.id) }
            }
        }
    }
}
