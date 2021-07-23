package com.example.myfavoritemovie.ui.movies

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.example.myfavoritemovie.domain.entity.Movie
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MoviesFirebaseAdapter(
    options: FirebaseRecyclerOptions<Movie>,
    private val viewModel: MovieViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val dataChanged: () -> Unit
) : FirebaseRecyclerAdapter<Movie, MovieViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.createFrom(parent, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, movie: Movie) {
        holder.bind(movie, viewModel)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        dataChanged()
    }
}