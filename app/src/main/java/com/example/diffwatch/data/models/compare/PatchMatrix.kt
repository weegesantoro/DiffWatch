package com.example.diffwatch.data.models.compare

data class PatchMatrix(
    var codeNumberLeft: Int?,
    val patchTextLeft: String?,
    var codeNumberRight: Int?,
    val patchTextRight: String?
)

