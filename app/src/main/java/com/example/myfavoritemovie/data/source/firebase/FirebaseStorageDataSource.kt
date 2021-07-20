package com.example.myfavoritemovie.data.source.firebase

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface FirebaseStorageDataSource {
    suspend fun uploadImage(image: Uri): StorageReference
}