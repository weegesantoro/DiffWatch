package com.example.diffwatch.data.models.pulls.extra


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Head(
    val label: String?,
    val ref: String?,
    val repo: RepoX?,
    val sha: String?,
    val user: UserX?
)