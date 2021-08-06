package com.example.diffwatch.ui.pulls

import androidx.lifecycle.ViewModel
import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.files.required.CommitFile

class PatchMatrixVM(val commitFile: CommitFile, val patchMatrix: PatchMatrix): ViewModel() {

    fun showHeader(): Boolean{
        return commitFile.patchMatrixList!![0] == patchMatrix
    }
}