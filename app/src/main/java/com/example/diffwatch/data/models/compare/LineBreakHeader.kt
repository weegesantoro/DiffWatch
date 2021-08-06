package com.example.diffwatch.data.models.compare

data class LineBreakHeader(
    val fullBreakHeaderText: String?,
    var negativeStartNumber: Int?,
    val positiveStartNumber: Int?,
    val blockLineCountLeft: Int?,
    val blockLineCountRight: Int?
)
