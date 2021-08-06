package com.example.diffwatch.data.models.files.required


import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.compare.SplitViewObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitFile(
    val additions: Int?,
    @Json(name = "blob_url")
    val blobUrl: String?,
    val changes: Int?,
    @Json(name = "contents_url")
    val contentsUrl: String?,
    val deletions: Int?,
    val filename: String?,
    val patch: String?,
    @Json(name = "raw_url")
    val rawUrl: String?,
    val sha: String?,
    val status: String?,

    @Transient
    var splitViewObject: SplitViewObject = SplitViewObject(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf()),
    @Transient
    var patchMatrixList: MutableList<PatchMatrix>? = mutableListOf(PatchMatrix(null,null,null,null))
)