package com.example.basicmovieapp.presentation.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.basicmovieapp.databinding.ListItemGenreBinding


class MovieGenreAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun submitGenres(genres: List<String>) {
        asyncListDiffer.submitList(genres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenreViewHolder(
            ListItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as GenreViewHolder).bindView(asyncListDiffer.currentList[position])

    inner class GenreViewHolder(private val binding: ListItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: String) {
            with(binding) {
                textViewGenre.text = item
            }
        }
    }
}