package com.example.myfavoritemovie.ui.movies.dialog

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.databinding.DialogMovieBinding
import com.example.myfavoritemovie.ui.ext.EventObserver

class MovieDialog : DialogFragment() {
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogMovieBinding.inflate(inflater, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = movieDialogViewModel

            movieDialogViewModel.selectedMovie.observe(viewLifecycleOwner) {
                TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
                movie = it
            }

            movieDialogViewModel.closeDialog.observe(viewLifecycleOwner, EventObserver {
                dismiss()
            })

            return root
        }
    }
}