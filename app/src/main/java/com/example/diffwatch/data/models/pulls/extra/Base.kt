package com.example.diffwatch.data.models.pulls.extra


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Base(
    val label: String?,
    val ref: String?,
    val repo: Repo?,
    val sha: String?,
    val user: User?
)