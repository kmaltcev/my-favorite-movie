package com.example.myfavoritemovie.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.databinding.FragmentSearchBinding
import com.example.myfavoritemovie.ui.ext.EventObserver
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.MoviesAdapter
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialog
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialogViewModel

class SearchFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<SearchViewModel> { viewModelsFactory }
    private val movieViewModel by viewModels<MovieViewModel> { viewModelsFactory }
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel> { viewModelsFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        val adapter = MoviesAdapter(movieViewModel, viewLifecycleOwner)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            searchViewModel = viewModel

            listMovies.layoutManager = LinearLayoutManager(requireContext())
            listMovies.adapter = adapter

            viewModel.searchResults
                .observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }

            val onSearch = {
                val query: String = editSearch.text.toString()
                viewModel.search(query)
            }

            movieViewModel.openMovieDialog.observe(viewLifecycleOwner, EventObserver {
                movieDialogViewModel.selectMovie(it)
                MovieDialog().show(childFragmentManager, null)
            })

            inputLayoutSearch.setEndIconOnClickListener {
                onSearch()
            }
            editSearch.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        onSearch()
                        true
                    }
                    else -> false
                }
            }
        }
        return binding.root
    }
}