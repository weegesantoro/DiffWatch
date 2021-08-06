package com.example.diffwatch.data.models.commits.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommitterX(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "events_url")
    val eventsUrl: String?,
    @Json(name = "followers_url")
    val followersUrl: String?,
    @Json(name = "following_url")
    val followingUrl: String?,
    @Json(name = "gists_url")
    val gistsUrl: String?,
    @Json(name = "gravatar_id")
    val gravatarId: String?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    val id: Int?,
    val login: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    @Json(name = "organizations_url")
    val organizationsUrl: String?,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String?,
    @Json(name = "repos_url")
    val reposUrl: String?,
    @Json(name = "site_admin")
    val siteAdmin: Boolean?,
    @Json(name = "starred_url")
    val starredUrl: String?,
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String?,
    val type: String?,
    val url: String?
)