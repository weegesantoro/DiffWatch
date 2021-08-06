package com.example.diffwatch.data.models.pulls.extra


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenseX(
    val key: String?,
    val name: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    @Json(name = "spdx_id")
    val spdxId: String?,
    val url: Any?
)