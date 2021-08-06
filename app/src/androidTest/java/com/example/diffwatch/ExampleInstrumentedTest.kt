package com.example.diffwatch

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.diffwatch.data.models.compare.LineBreakHeader
import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.compare.PatchRows
import com.example.diffwatch.data.models.compare.SplitViewObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.diffwatch", appContext.packageName)

        println("did my first test")
        //test()
        secondTest()
    }


    private fun test(){


        val examplePatch = "@@ -1,11 +1,8 @@\n apply plugin: 'me.champeau.gradle.japicmp'\n-applyOsgi(this)\n \n-jar {\n-  // MANIFEST.MF, including OSGi bnd instructions.\n-  // We export okhttp3.internal for our own modules use.\n-  // The packages of all optional dependencies must be explicitly specified.\n-  bnd '''\n+Projects.applyOsgi(\n+  project,\n+  '''\n   Export-Package: \\\\\n   okhttp3,\\\\\n   okhttp3.internal.*;okhttpinternal=true;mandatory:=okhttpinternal\n@@ -21,7 +18,7 @@ jar {\n   Automatic-Module-Name: okhttp3\n   Bundle-SymbolicName: com.squareup.okhttp3\n   '''\n-}\n+)\n \n sourceSets {\\n   main.java.srcDirs += \\\"\$buildDir/generated/sources/java-templates/java/main\\"
        val patchArray: MutableList<String>
        patchArray = examplePatch.split("\n").toCollection(ArrayList())

        val splitPatchObject = SplitViewObject(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())

        println("patchArray = ")
        for(patchLine in patchArray){
            println(patchLine)
        }


        // clone patchArray into left and right array for split view
        for(patchLine in patchArray){
            splitPatchObject.leftArray.add(PatchRows(null, patchLine))
            splitPatchObject.rightArray.add(PatchRows(null, patchLine))
        }

        println("first filled splitPatchObject.leftArray = ")
        println(splitPatchObject.leftArray)
        println("first filled splitPatchObject.rightArray = ")
        println(splitPatchObject.rightArray)


        splitPatchObject.leftArray.removeIf{ it.patchText!!.startsWith("+")}
        splitPatchObject.rightArray.removeIf{ it.patchText!!.startsWith("-")}



        println("splitPatchObject.leftArray after removal= ")
        println(splitPatchObject.leftArray)
        println("splitPatchObject.rightArray after removal= ")
        println(splitPatchObject.rightArray)




        // set correct code line numbers - left
        splitPatchObject.leftArray.forEachIndexed { index, patchRows ->
            patchRows.codeNumber = index + 1
            println("*L* patchRows.codeNumber = ${patchRows.codeNumber}")
        }


        // set correct code line numbers - right
        splitPatchObject.rightArray.forEachIndexed { index, patchRows ->
            patchRows.codeNumber = index + 1
            println("*R* patchRows.codeNumber = ${patchRows.codeNumber}")
        }

        // add blanks in
        splitPatchObject.leftArray.forEachIndexed { index, _ ->
            if(!splitPatchObject.rightArray[index].patchText!!.startsWith("+")){
                splitPatchObject.rightArray.add(index, PatchRows(null, "   ---   blank   ---")) // blank text Right
            }
        }

        // add blanks in
        splitPatchObject.rightArray.forEachIndexed { index, _ ->
            if(!splitPatchObject.leftArray[index].patchText!!.startsWith("-")){
                splitPatchObject.leftArray.add(index, PatchRows(null, "   ---   blank   ---")) // blank text Right
            }
        }


        printArrays(splitPatchObject.leftArray, splitPatchObject.rightArray)










    }





    private fun printArrays(leftArray: MutableList<PatchRows>, rightArray: MutableList<PatchRows>) {

        for(i in leftArray.indices){
            println(" *Left*                     ${leftArray[i].codeNumber}     ${leftArray[i].patchText}")
        }

        for(i in rightArray.indices){
            println(" *Right*                     ${rightArray[i].codeNumber}     ${rightArray[i].patchText}")
        }

    }


    private fun secondTest(){

        println("starting second test")


        val examplePatch = "@@ -1,11 +1,8 @@\n apply plugin: 'me.champeau.gradle.japicmp'\n-applyOsgi(this)\n \n-jar {\n-  // MANIFEST.MF, including OSGi bnd instructions.\n-  // We export okhttp3.internal for our own modules use.\n-  // The packages of all optional dependencies must be explicitly specified.\n-  bnd '''\n+Projects.applyOsgi(\n+  project,\n+  '''\n   Export-Package: \\\\\n   okhttp3,\\\\\n   okhttp3.internal.*;okhttpinternal=true;mandatory:=okhttpinternal\n@@ -21,7 +18,7 @@ jar {\n   Automatic-Module-Name: okhttp3\n   Bundle-SymbolicName: com.squareup.okhttp3\n   '''\n-}\n+)\n \n sourceSets {\\n   main.java.srcDirs += \\\"\$buildDir/generated/sources/java-templates/java/main\\"

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
            //println("patchline = $patchLine")
            if(patchLine.startsWith("@@")){
                var stringParse = patchLine.replace("@@","")
                val delineatorsArray = stringParse.split(" ", ",").toCollection(ArrayList())
                var lineDelineators = LineBreakHeader(patchLine, Math.abs(delineatorsArray[1].toInt()), delineatorsArray[3].toInt(), delineatorsArray[2].toInt(), delineatorsArray[4].toInt())
                left_start = lineDelineators.negativeStartNumber!!
                right_start = lineDelineators.positiveStartNumber!!
            }else{
                if(patchLine.startsWith("-")){
                    minus++
                    splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                    left_start++
                }
                if(patchLine.startsWith("+")){
                    plus++
                    splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))
                    right_start++
                }
                if(!patchLine.startsWith("+") && !patchLine.startsWith("+")){
                    if(minus == plus) {
                        minus = 0
                        plus = 0
                        splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                        splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))
                    }

                    if(minus > plus) {
                        var nulls = minus - plus
                        for(i in 1..nulls) {
                            splitPatchObject.rightArray.add(PatchRows(null, null))
                            plus++
                        }
                        splitPatchObject.leftArray.add(PatchRows(left_start, patchLine))
                        splitPatchObject.rightArray.add(PatchRows(right_start, patchLine))
                    }

                    if(minus < plus) {
                        var nulls = plus - minus
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



        printArrays(splitPatchObject.leftArray, splitPatchObject.rightArray)
    }



}