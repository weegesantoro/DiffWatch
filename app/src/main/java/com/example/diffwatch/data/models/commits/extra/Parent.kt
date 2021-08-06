package com.example.diffwatch.data.models.commits.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Parent(
    @Json(name = "html_url")
    val htmlUrl: String?,
    val sha: String?,
    val url: String?
)