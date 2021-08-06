package com.example.diffwatch.data.models.compare

data class SplitViewObject(
    val leftArray: MutableList<PatchRows>,
    val rightArray: MutableList<PatchRows>,
    val lineBreakHeaders: MutableList<LineBreakHeader>?,
    val patchMatrixList: MutableList<PatchMatrix>?
)