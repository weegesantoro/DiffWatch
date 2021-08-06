package com.example.diffwatch.data.models.files.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Verification(
    val payload: Any?,
    val reason: String?,
    val signature: Any?,
    val verified: Boolean?
)