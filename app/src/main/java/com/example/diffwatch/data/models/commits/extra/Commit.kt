package com.example.diffwatch.data.models.commits.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Commit(
    val author: AuthorX?,
    @Json(name = "comment_count")
    val commentCount: Int?,
    val committer: Committer?,
    val message: String?,
    val tree: Tree?,
    val url: String?,
    val verification: Verification?
)