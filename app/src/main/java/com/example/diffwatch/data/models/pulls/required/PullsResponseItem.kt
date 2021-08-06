package com.example.diffwatch.data.models.pulls.required


import com.example.diffwatch.data.models.pulls.extra.Base
import com.example.diffwatch.data.models.pulls.extra.Head
import com.example.diffwatch.data.models.pulls.extra.Links
import com.example.diffwatch.data.models.pulls.extra.UserXX
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PullsResponseItem(
    @Json(name = "active_lock_reason")
    val activeLockReason: Any?,
    val assignee: Any?,
    val assignees: List<Any>?,
    @Json(name = "author_association")
    val authorAssociation: String?,
    @Json(name = "auto_merge")
    val autoMerge: Any?,
    val base: Base?,
    val body: String?,
    @Json(name = "closed_at")
    val closedAt: Any?,
    @Json(name = "comments_url")
    val commentsUrl: String?,
    @Json(name = "commits_url")
    val commitsUrl: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "diff_url")
    val diffUrl: String?,
    val draft: Boolean?,
    val head: Head?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    val id: Int?,
    @Json(name = "issue_url")
    val issueUrl: String?,
    val labels: List<Any>?,
    @Json(name = "_links")
    val links: Links?,
    val locked: Boolean?,
    @Json(name = "merge_commit_sha")
    val mergeCommitSha: String?,
    @Json(name = "merged_at")
    val mergedAt: Any?,
    val milestone: Any?,
    @Json(name = "node_id")
    val nodeId: String?,
    val number: Int?,
    @Json(name = "patch_url")
    val patchUrl: String?,
    @Json(name = "requested_reviewers")
    val requestedReviewers: List<Any>?,
    @Json(name = "requested_teams")
    val requestedTeams: List<Any>?,
    @Json(name = "review_comment_url")
    val reviewCommentUrl: String?,
    @Json(name = "review_comments_url")
    val reviewCommentsUrl: String?,
    val state: String?,
    @Json(name = "statuses_url")
    val statusesUrl: String?,
    val title: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    val url: String?,
    val user: UserXX?
)