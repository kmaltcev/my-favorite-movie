package com.example.myfavoritemovie.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.databinding.FragmentUpcomingBinding
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesAdapter

class UpcomingFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<UpcomingViewModel> { viewModelsFactory }
    private val movieViewModel by viewModels<MovieViewModel> { viewModelsFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val adapter = MoviesAdapter(movieViewModel, viewLifecycleOwner)
        viewModel.search("RU")
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            upcomingViewModel = viewModel

            listMovies.layoutManager = LinearLayoutManager(requireContext())
            listMovies.adapter = adapter

            viewModel.upcomingMovies
                .observe(viewLifecycleOwner) { movies ->
                    adapter.submitList(movies)
                }
            return root
        }
    }
}