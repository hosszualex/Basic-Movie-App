package com.example.basicmovieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.basicmovieapp.databinding.ListItemMovieBinding
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.domain.util.TextFormatterUtil
import com.example.basicmovieapp.domain.util.roundToHalf


class MovieListAdapter(
    private val listener: IOnMovieClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun refreshData(data: List<Movie>) {
        asyncListDiffer.submitList(data.map { it.copy() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StaffPickMovieViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as StaffPickMovieViewHolder).bindView(asyncListDiffer.currentList[position])

    inner class StaffPickMovieViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(binding) {
                imageViewPoster.load(item.posterUrl) {
                    crossfade(true)
                }
                root.setOnClickListener {
                    listener.onMovieClicked(item.id)
                }
                textViewMovieTitle.text = item.title
                textViewReleaseDate.text = TextFormatterUtil.getYearFromString(item.releaseDate)
                ratingBarMovie.rating = item.rating.roundToHalf().toFloat()
                imageButtonFavoriteIcon.isSelected = item.isFavourite
                imageButtonFavoriteIcon.setOnClickListener {
                    listener.onToggleFavorites(item.id)
                }
            }
        }
    }
}