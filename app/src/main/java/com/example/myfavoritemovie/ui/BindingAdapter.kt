package com.example.myfavoritemovie.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myfavoritemovie.domain.entity.Image

@BindingAdapter("srcCompat")
fun setImage(imageView: ImageView, image: Image?) {

    image?.let {
        Glide.with(imageView)
            .load(image.reference)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    } ?: imageView.setImageDrawable(null)
}