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


/*
        $arrayLeft = [];
        $arrayRight = [];


        foreach($patchArray as $patchLine) {
            if(stristr($patchLine,"@@ -")) {

            $line_numbers = str_replace(["@@ -","+"," @@",'"'],"",trim($patchLine));
            $line_numbers = explode(" ",$line_numbers);

            $leftlinenumbers = explode(",",$line_numbers[0]);
            $left_start = $leftlinenumbers[0];

            $rightlinenumbers = explode(",",$line_numbers[1]);
            $right_start = $rightlinenumbers[0];


            $arrayLeft[] = $patchLine;
            $arrayRight[] = null;
        } else {

            if($patchLine[0] == "-") {
            $minus++;
            $arrayLeft[] = $left_start." ".$patchLine;
            $left_start++;
        }
            if($patchLine[0] == "+") {
            $plus++;
            $arrayRight[] = $right_start." ".$patchLine;
            $right_start++;
        }
            if($patchLine[0] != "+" && $patchLine[0] != "-") {
            if($minus == $plus) {
            $minus = $plus = 0;
            $arrayLeft[] = $left_start." ".$patchLine;
            $arrayRight[] = $right_start." ".$patchLine;
        }
            if($minus > $plus) {
            $nulls = $minus-$plus;
            for($n = 0;$n<$nulls;$n++) {
            $arrayRight[] = null;
            $plus++;
        }
            $arrayLeft[] = $left_start." ".$patchLine;
            $arrayRight[] = $right_start." ".$patchLine;
        }
            if($minus < $plus) {
            $nulls = $plus-$minus;
            for($n = 0;$n<$nulls;$n++) {
            $arrayLeft[] = null;
            $minus++;
        }
            $arrayLeft[] = $left_start." ".$patchLine;
            $arrayRight[] = $right_start." ".$patchLine;
        }
            $left_start++;
            $right_start++;
        }

        }
        }


 */









/*


        // clone patchArray into left and right array for split view
        for(patchLine in patchArray){

            if(patchLine.startsWith("@@")){
                splitPatchObject.lineBreakHeaders?.add(parseDelineators(patchLine))
            }else{
                splitPatchObject.leftArray.add(PatchRows(null, patchLine))
                splitPatchObject.rightArray.add(PatchRows(null, patchLine))
            }

        }

        // print progress so far
        checkProgress(splitPatchObject, "first filled splitPatchObject.leftArray = ")
        checkProgress(splitPatchObject, "first filled splitPatchObject.rightArray = ")


        // check build device and remove appropriate (+/-)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            splitPatchObject.leftArray.removeIf{ it.patchText!!.startsWith("+") }
            splitPatchObject.rightArray.removeIf{ it.patchText!!.startsWith("-")}
        }else{
            calculateForOlderPhones(splitPatchObject)
        }

        // print progress after (+/-) removal
        checkProgress(splitPatchObject, "splitPatchObject.leftArray after removal = ")
        checkProgress(splitPatchObject, "splitPatchObject.rightArray after removal = ")



        // set correct code line numbers - left
        splitPatchObject.leftArray.forEachIndexed { index, patchRows ->
            if(splitPatchObject.lineBreakHeaders != null){
                patchRows.codeNumber = index + (splitPatchObject.lineBreakHeaders[index].negativeStartNumber?.toInt() ?: 0)
                println("*L* patchRows.codeNumber = ${patchRows.codeNumber}")
            }
        }


        // set correct code line numbers - right
        splitPatchObject.rightArray.forEachIndexed { index, patchRows ->
            if(splitPatchObject.lineBreakHeaders != null){
                patchRows.codeNumber = index + (splitPatchObject.lineBreakHeaders[index].positiveStartNumber?.toInt() ?: 0)
                println("*R* patchRows.codeNumber = ${patchRows.codeNumber}")
            }
        }

        splitPatchObject.apply {

            // add blank spaces where necessary on right side
            leftArray.forEachIndexed { index, _ ->
                if( // *** if leftArray starts with (-) and rightArray does NOT start with (+)
                    leftArray[index].patchText!!.startsWith("-") && !rightArray[index].patchText!!.startsWith("+")){
                    rightArray.add(index, PatchRows(null, "   ---   blank   ---")) // blank text Right
                }
            }

            // add blank spaces where necessary on left side
            rightArray.forEachIndexed { index, _ ->
                if(// *** if leftArray starts with (-) and rightArray does NOT start with (+)
                    rightArray[index].patchText!!.startsWith("+") && !leftArray[index].patchText!!.startsWith("-")){
                    leftArray.add(index, PatchRows(null, "   ---   blank   ---")) // blank text Right
                }
            }

        }


        // print progress after adding blanks
        checkProgress(splitPatchObject, "splitPatchObject.leftArray after adding blanks = ")
        checkProgress(splitPatchObject, "splitPatchObject.rightArray after adding blanks = ")


        splitPatchObject.apply {
            for(i in leftArray.indices){
                patchMatrixList?.add(PatchMatrix(leftArray[i].codeNumber, leftArray[i].patchText, rightArray[i].codeNumber, rightArray[i].patchText))
            }
        }

 */


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


    private fun parseDelineators(patchLine: String): LineBreakHeader{

        var stringParse = patchLine.replace("@@","")
        val delineatorsArray = stringParse.split(" ", ",").toCollection(ArrayList())
        return LineBreakHeader(patchLine, delineatorsArray[1].toInt(), delineatorsArray[3].toInt(), delineatorsArray[2].toInt(), delineatorsArray[4].toInt())
    }




    private fun calculateForOlderPhones(splitPatchObject: SplitViewObject) {
        splitPatchObject.apply {

            // remove (+) from left
            val removingPlus: ArrayList<PatchRows> = arrayListOf()
            for (patchLine in leftArray){
                if (patchLine.patchText!!.contains("+")){
                    removingPlus.add(patchLine)
                }
            }
            leftArray.removeAll(removingPlus)

            // remove (-) from right
            val removingMinus: ArrayList<PatchRows> = arrayListOf()
            for (patchLine in rightArray){
                if (patchLine.patchText!!.contains("-")){
                    removingMinus.add(patchLine)
                }
            }
            rightArray.removeAll(removingMinus)

        }
    }


}