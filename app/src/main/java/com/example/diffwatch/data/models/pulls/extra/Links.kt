package com.example.diffwatch.data.models.pulls.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val comments: Comments?,
    val commits: Commits?,
    val html: Html?,
    val issue: Issue?,
    @Json(name = "review_comment")
    val reviewComment: ReviewComment?,
    @Json(name = "review_comments")
    val reviewComments: ReviewComments?,
    val self: Self?,
    val statuses: Statuses?
)