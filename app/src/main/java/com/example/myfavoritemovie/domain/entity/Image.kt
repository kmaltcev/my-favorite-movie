package com.example.myfavoritemovie.domain.entity

import android.net.Uri
import com.google.firebase.storage.StorageReference

sealed class Image {
    abstract val reference: Any
}

data class UriImage(override val reference: Uri) : Image() {
    override fun toString(): String {
        return reference.toString()
    }
}

data class StorageReferenceImage(override val reference: StorageReference) : Image() {
    override fun toString(): String {
        return reference.path
    }
}