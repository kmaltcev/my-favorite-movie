package com.example.myfavoritemovie.ui.movies.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemovie.databinding.ItemPosterBinding
import com.example.myfavoritemovie.domain.entity.Image

open class PostersAdapter(
    private val viewModel: MovieDialogViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Image, PostersAdapter.PosterViewHolder>(PosterDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterViewHolder {
        return PosterViewHolder.createFrom(parent, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    private class PosterDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

    }

    class PosterViewHolder private constructor(
        private val binding: ItemPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: Image, viewModel: MovieDialogViewModel) {
            binding.poster = poster
            binding.viewModel = viewModel
        }

        companion object {
            fun createFrom(parent: ViewGroup, lifecycleOwner: LifecycleOwner): PosterViewHolder {
                val itemBinding =
                    ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                itemBinding.lifecycleOwner = lifecycleOwner
                return PosterViewHolder(itemBinding)
            }
        }
    }
}