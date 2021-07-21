package com.example.myfavoritemovie.data.source.firebase

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.net.URL
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseStorageDataSource {

    private val postersStorage by lazy { Firebase.storage.reference.child(PATH_POSTERS) }

    suspend fun uploadImage(image: Uri): StorageReference =
        suspendCoroutine { continuation ->
            val imageName = URL(image.toString()).readBytes()
            val imageReference = buildPosterReference(imageName)
            imageReference.putStream(URL(image.toString()).openStream())
                .addOnSuccessListener {
                    continuation.resume(imageReference)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(RuntimeException("Query was cancelled! ${it.message}"))
                }
        }

    private fun buildPosterReference(imageName: ByteArray): StorageReference {
        val internalId = UUID.nameUUIDFromBytes(imageName)
        return postersStorage.child("$internalId.jpg")
    }

    private companion object {
        const val PATH_POSTERS = "posters"
    }
}