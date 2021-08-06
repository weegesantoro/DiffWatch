package com.example.diffwatch.ui.pulls

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffwatch.R
import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.files.required.CommitFile

class PatchMatrixVM(val commitFile: CommitFile, val patchMatrix: PatchMatrix): ViewModel() {

    val colorLeft: MutableLiveData<Int> = MutableLiveData(R.color.white)
    val colorRight: MutableLiveData<Int> = MutableLiveData(R.color.white)

    init {
        colorLeft.value = colorBackgroundLeft()
        colorRight.value = colorBackgroundRight()
    }

    fun showHeader(): Boolean{
        return commitFile.patchMatrixList!![0] == patchMatrix
    }
    fun colorBackgroundLeft(): Int {
        return when {
            patchMatrix.patchTextLeft != null && patchMatrix.patchTextLeft.startsWith("-") -> {
                R.color.github_red
            }
            patchMatrix.patchTextLeft != null && patchMatrix.patchTextLeft.startsWith("@@ -") -> {
                R.color.github_blue
            }
            patchMatrix.patchTextLeft == null && patchMatrix.patchTextRight != null -> {
                R.color.github_grey
            }
            else -> {
                R.color.white
            }
        }
    }

    fun colorBackgroundRight(): Int {
        return when {
            patchMatrix.patchTextRight != null && patchMatrix.patchTextRight.startsWith("+") -> {
                R.color.github_green
            }
            patchMatrix.patchTextLeft != null && patchMatrix.patchTextLeft.startsWith("@@ -") -> {
                R.color.github_blue
            }
            patchMatrix.patchTextRight == null && patchMatrix.patchTextLeft != null -> {
                R.color.github_grey
            }
            else -> {
                R.color.white
            }
        }
    }


}