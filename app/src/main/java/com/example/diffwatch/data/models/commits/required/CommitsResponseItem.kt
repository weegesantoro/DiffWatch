package com.example.diffwatch.data.models.commits.required


import com.example.diffwatch.data.models.commits.extra.Author
import com.example.diffwatch.data.models.commits.extra.Commit
import com.example.diffwatch.data.models.commits.extra.CommitterX
import com.example.diffwatch.data.models.commits.extra.Parent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitsResponseItem(
    val author: Author?,
    @Json(name = "comments_url")
    val commentsUrl: String?,
    val commit: Commit?,
    val committer: CommitterX?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    val parents: List<Parent>?,
    val sha: String?,
    val url: String?
)