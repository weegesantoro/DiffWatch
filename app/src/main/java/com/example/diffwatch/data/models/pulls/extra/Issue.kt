package com.example.diffwatch.data.models.pulls.extra


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Issue(
    val href: String?
)