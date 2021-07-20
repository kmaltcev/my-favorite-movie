package com.example.myfavoritemovie.ui.movies.added

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.app.app
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.data.repository.converters.parseMovie
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.databinding.FragmentMoviesBinding
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.EventObserver
import com.example.myfavoritemovie.ui.linkmovie.LinkMovieViewModel
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesFirebaseAdapter
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialog
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialogViewModel

abstract class AddedMoviesFragment : Fragment() {

    private val viewModelFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<MovieViewModel> { viewModelFactory }
    private val linkMovieViewModel by activityViewModels<LinkMovieViewModel> { viewModelFactory }
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel> { viewModelFactory}

    private val adapter by lazy { createMoviesAdapter() }

    private lateinit var layoutManager: LinearLayoutManager
    private var listMovieState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        binding.textTitle.text = getTitle()

        layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }

        binding.listMovies.layoutManager = layoutManager
        binding.listMovies.adapter = adapter

        viewModel.onLinkMovie.observe(viewLifecycleOwner, EventObserver {
            linkMovieViewModel.searchByMovie(it)
            findNavController().navigate(R.id.linkMovieFragment)
        })

        viewModel.openMovieDialog.observe(viewLifecycleOwner, EventObserver {
            movieDialogViewModel.selectMovie(it)
            MovieDialog().show(childFragmentManager, null)
        })

        return binding.root
    }

    private fun createMoviesAdapter(): MoviesFirebaseAdapter {
        val firebaseRealtimeDatabase = requireContext().app.moviesModule.firebaseRealtimeDatabase

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(getMoviesQuery(firebaseRealtimeDatabase).orderByChild("dateAdded"), ::parseMovie)
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