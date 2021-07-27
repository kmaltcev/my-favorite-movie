package com.example.myfavoritemovie.ui.movies.added

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfavoritemovie.app.app
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.data.converters.parseMovie
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.databinding.FragmentMoviesBinding
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.ext.EventObserver
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesFirebaseAdapter
import com.example.myfavoritemovie.ui.dialog.MovieDialog
import com.example.myfavoritemovie.ui.dialog.MovieDialogViewModel
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query

abstract class AddedMoviesFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<MovieViewModel> { viewModelsFactory }
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel> { viewModelsFactory }

    private val adapter by lazy { createMoviesAdapter() }

    private lateinit var layoutManager: LinearLayoutManager
    private var listMovieState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        with(binding) {
            textTitle.text = getTitle()

            layoutManager = LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }

            listMovies.layoutManager = layoutManager
            listMovies.adapter = adapter

            viewModel.openMovieDialog.observe(viewLifecycleOwner, EventObserver {
                movieDialogViewModel.selectMovie(it)
                MovieDialog().show(childFragmentManager, null)
            })

            return root
        }
    }

    private fun createMoviesAdapter(): MoviesFirebaseAdapter {
        val firebaseRealtimeDatabase = requireContext().app.moviesModule.firebaseRealtimeDatabase

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(
                getMoviesQuery(firebaseRealtimeDatabase).orderByChild("dateAdded"),
                ::parseMovie
            )
            .setLifecycleOwner(this)
            .build()

        return MoviesFirebaseAdapter(options, viewModel, this) {
            listMovieState?.let { layoutManager.onRestoreInstanceState(it) }
            listMovieState = null
        }
    }

    override fun onStop() {
        super.onStop()
        listMovieState = layoutManager.onSaveInstanceState()
    }

    protected abstract fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query
    protected abstract fun getTitle(): String
}