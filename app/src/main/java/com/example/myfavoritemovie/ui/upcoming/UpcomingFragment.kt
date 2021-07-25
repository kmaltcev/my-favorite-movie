package com.example.myfavoritemovie.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.data.service.UpcomingMoviesReceiver
import com.example.myfavoritemovie.databinding.FragmentUpcomingBinding
import com.example.myfavoritemovie.ui.EventObserver
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesAdapter
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialog
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialogViewModel

class UpcomingFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<UpcomingViewModel> { viewModelsFactory }
    private val movieViewModel by viewModels<MovieViewModel> { viewModelsFactory }
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel> { viewModelsFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val adapter = MoviesAdapter(movieViewModel, viewLifecycleOwner)

        viewModel.search("GB")

        with(binding) {
            textTitle.text = getString(R.string.upcoming_title)
            lifecycleOwner = viewLifecycleOwner
            upcomingViewModel = viewModel

            listMovies.layoutManager = LinearLayoutManager(requireContext())
            listMovies.adapter = adapter

            movieViewModel.openMovieDialog.observe(viewLifecycleOwner, EventObserver {
                movieDialogViewModel.selectMovie(it)
                MovieDialog().show(childFragmentManager, null)
            })

            viewModel.upcomingMovies
                .observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }

            activity?.stopService(
                Intent(
                    activity?.applicationContext,
                    UpcomingMoviesReceiver::class.java
                )
            )
            return root
        }
    }

    override fun onDestroy() {
        activity?.startService(
            Intent(
                activity?.applicationContext,
                UpcomingMoviesReceiver::class.java
            )
        )
        super.onDestroy()
    }
}