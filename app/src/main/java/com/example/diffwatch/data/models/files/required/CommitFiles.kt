package com.example.diffwatch.data.models.files.required


import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.compare.SplitViewObject
import com.example.diffwatch.data.models.files.extra.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitFiles(
    val sha: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    val commit: Commit?,
    val url: String?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    @Json(name = "comments_url")
    val commentsUrl: String?,
    val author: Author?,
    val committer: CommitterX?,
    val parents: List<Parent>?,
    val stats: Stats?,
    val files: List<CommitFile>?,

    @Transient
    var patchMatrixList: MutableList<PatchMatrix> = mutableListOf(PatchMatrix(null,null,null,null))
)