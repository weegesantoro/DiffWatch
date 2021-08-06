package com.example.diffwatch.data.models.files.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stats(
    val additions: Int?,
    val deletions: Int?,
    val total: Int?
)