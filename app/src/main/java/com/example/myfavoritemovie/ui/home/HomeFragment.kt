package com.example.myfavoritemovie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.buttonFavoriteMovies.setOnClickListener {
            findNavController().navigate(R.id.toFavoriteMoviesFragment)
        }
        binding.buttonNeedToWatchMovies.setOnClickListener {
            findNavController().navigate(R.id.toNeedToWatchMoviesFragment)
        }
        binding.buttonSearchMovie.setOnClickListener {
            findNavController().navigate(R.id.toSearchFragment)
        }
        return binding.root
    }
}