package com.example.diffwatch.data.models.files.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorX(
    val date: String?,
    val email: String?,
    val name: String?
)