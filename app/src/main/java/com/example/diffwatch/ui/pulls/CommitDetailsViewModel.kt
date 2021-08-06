package com.example.diffwatch.ui.pulls

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffwatch.data.models.compare.LineBreakHeader
import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.compare.PatchRows
import com.example.diffwatch.data.models.compare.SplitViewObject
import com.example.diffwatch.data.models.files.required.CommitFiles
import com.example.diffwatch.data.network.GitHubApi
import kotlinx.coroutines.*
import java.lang.Math.abs

class CommitDetailsViewModel : ViewModel() {




    //private val _placeHolder = MutableLiveData<String>()
    //val placeholder: LiveData<String> = _placeHolder

    var commitNumber: String? = null
    var commitUrl: String? = null


    private val _commitFiles = MutableLiveData<CommitFiles>()
    val commitFiles: LiveData<CommitFiles> = _commitFiles


    fun sendCommitDetailsRequest(url: String) {

        println("commitDetailsUrl = $url")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeout(30000){
                    val response = GitHubApi.API.retrofitService.getCommitDetailsResponse(url)
                    withContext(Dispatchers.Main){
                        _commitFiles.value = response.body()
                        println("patch.response.body()?.data = ${response.body()}")
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }



    fun reconstructSplitViewStrings(examplePatch: String): MutableList<PatchMatrix>? {


        val patchArray: MutableList<String>
        patchArray = examplePatch.split("\n").toCollection(ArrayList())

        val splitPatchObject = SplitViewObject(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())

        // first print of split array
        println("patchArray = ")
        for(patchLine in patchArray){
            println(patchLine)
        }

        var minus = 0
        var plus = 0
        var left_start = 0
        var right_start = 0


        for(patchLine in patchArray){
            if(patchLine.startsWith("@@")){
                var stringParse = patchLine.replace("@@","")
                val delineatorsArray = stringParse.split(" ", ",").toCollection(ArrayList())
                var lineDelineators = LineBreakHeader(patchLine, abs(delineatorsArray[1].toInt()), delineatorsArray[3].toInt(), delineatorsArray[2].toInt(), delineatorsArray[4].toInt())
                left_start = lineDelineators.negativeStartNumber!!
                right_start = lineDelineators.positiveStartNumber!!
                splitPatchObject.leftArray.add(PatchRows(null, patchLine))
                splitPatchObject.rightArray.add(PatchRows(null, null))

                println("plusminus @@@@@@@@@@@@@@@:left:"+left_start +" right:"+right_start);

            }else{
                if(patchLine.startsWith("-")){
                    minus++
                    splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                    left_start++
                    println("plusminus ------------:"+patchLine);
                }
                if(patchLine.startsWith("+")){
                    plus++
                    splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))
                    right_start++
                    println("plusminus ++++++:"+patchLine);
                }
                if(!patchLine.startsWith("+") && !patchLine.startsWith("-")){
                    if(minus == plus) {
                        println("plusminus minus == plus:"+patchLine);

                        minus = 0
                        plus = 0
                        splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                        splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))

                    }

                    if(minus > plus) {
                        var nulls = minus - plus
                        println(">> plusminus   nulls:"+nulls+" minus:"+minus+" plus:"+plus);
                        for(i in 1..nulls) {
                            splitPatchObject.rightArray.add(PatchRows(null, null))
                            plus++
                        }
                        splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                        splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))


                    }

                    if(minus < plus) {
                        var nulls = plus - minus
                        println("<< plusminus   nulls:"+nulls+" minus:"+minus+" plus:"+plus);
                        for(i in 1..nulls) {
                            splitPatchObject.leftArray.add(PatchRows(null, null))
                            minus++
                        }
                        splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                        splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))

                    }

                    left_start++
                    right_start++



                }
            }
        }

        splitPatchObject.leftArray.forEachIndexed { index, patchRows ->
            println("${patchRows.codeNumber}  ${patchRows.patchText}  ${splitPatchObject.rightArray[index].codeNumber}  ${splitPatchObject.rightArray[index].patchText}")
            splitPatchObject.patchMatrixList?.add(PatchMatrix(patchRows.codeNumber,patchRows.patchText,splitPatchObject.rightArray[index].codeNumber, splitPatchObject.rightArray[index].patchText))
        }





        return splitPatchObject.patchMatrixList
    }

    private fun checkProgress(splitPatchObject: SplitViewObject, message: String) {

        splitPatchObject.apply {
            println(message)
            printArray(leftArray)
            println(message)
            printArray(rightArray)
        }
    }


    private fun printArray(array: MutableList<PatchRows>) {

        for(patchLine in array){
            print("        ***  ${patchLine.codeNumber}")
            println("     ${patchLine.patchText}")
        }
    }




}