package com.example.myfavoritemovie.ui.movies.dialog

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.app.dependency.ViewModelFactory
import com.example.myfavoritemovie.databinding.DialogMovieBinding
import com.example.myfavoritemovie.ui.EventObserver
import com.example.myfavoritemovie.ui.ext.hide
import com.example.myfavoritemovie.ui.ext.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class MovieDialog : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<MovieDialogViewModel> {
        ViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val postersAdapter = PostersAdapter(viewModel, viewLifecycleOwner)

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            binding.headerMarker.hide()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.pagerPosters.hide()
            binding.chipsNames.hide()
            binding.groupLineSeparator.hide()
            binding.movie = it
        }

        viewModel.posters.observe(viewLifecycleOwner, EventObserver {
            postersAdapter.submitList(it)
            binding.headerMarker.show()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.textSeparator.text = getString(R.string.select_poster)
            binding.groupLineSeparator.show()
            binding.chipsNames.hide()
            binding.pagerPosters.show()
        })

        viewModel.names.observe(viewLifecycleOwner, EventObserver { names ->

            binding.chipsNames.run {
                removeAllViews()
                names.forEach { name ->
                    addView(buildNameChip(name))
                }
            }

            binding.headerMarker.show()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.textSeparator.text = getString(R.string.select_name)
            binding.groupLineSeparator.show()
            binding.pagerPosters.hide()
            binding.chipsNames.show()
        })

        viewModel.closeDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })

        binding.pagerPosters.adapter = postersAdapter

        return binding.root
    }

    private fun buildNameChip(name: String) = Chip(requireContext())
        .apply {
            text = name
            setOnClickListener { viewModel.changeName(text.toString()) }
        }

    override fun onStart() {
        super.onStart()

        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}