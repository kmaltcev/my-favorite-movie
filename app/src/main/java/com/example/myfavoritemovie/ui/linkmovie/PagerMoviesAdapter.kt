package com.example.myfavoritemovie.ui.linkmovie

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.movies.MovieViewHolder
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesAdapter

class PagerMoviesAdapter(
    viewModel: MovieViewModel,
    lifecycleOwner: LifecycleOwner
) : MoviesAdapter(
    viewModel,
    lifecycleOwner
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return super.onCreateViewHolder(parent, viewType).apply {
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    fun getMovie(position: Int): Movie = getItem(position)
}