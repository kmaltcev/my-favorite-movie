package com.example.myfavoritemovie.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemovie.databinding.ItemMovieBinding
import com.example.myfavoritemovie.domain.entity.Movie

class MovieViewHolder private constructor(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, viewModel: MovieViewModel) {
        binding.movie = movie
        binding.viewModel = viewModel

    }

    companion object {
        fun createFrom(parent: ViewGroup, lifecycleOwner: LifecycleOwner): MovieViewHolder {
            val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            itemBinding.lifecycleOwner = lifecycleOwner
            return MovieViewHolder(itemBinding)
        }
    }
}